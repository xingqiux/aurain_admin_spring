package top.xkqq.service.impl;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xkqq.properties.SmsProperties;
import top.xkqq.service.SmsService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SmsProperties properties;


    // 设置验证码
    @Override
    public void sendValidateCode(String phone) {

        // 检查缓存中是否有号码验证码
        String code = redisTemplate.opsForValue().get("phone:code:" + phone);
        log.info("号码{},检查缓存中是否有号码验证码", phone);
        if (StringUtils.hasText(code)) {
            log.info("号码{},缓存中存在号码验证码", phone);
            return;
        }

        String validateCode = RandomStringUtils.randomNumeric(4);      // 生成验证码
        log.info("验证码为:{},开始发送短信", validateCode);
        sendSms(phone, validateCode); // 发送短信验证码
        log.info("验证码为:{},已发送,存储到redis中", validateCode);
        // 存储
        redisTemplate.opsForValue().set("phone:code:" + phone, validateCode, 5, TimeUnit.MINUTES);

    }

    @Override
    public void sendSms(String phone, String validateCode) {
        // 设置认证权限信息
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(properties.getAccessKeyId())
                .accessKeySecret(properties.getAccessKeySecret())
                .build());

        // 设定客户信息
        AsyncClient client = AsyncClient.builder()
                .region("cn-qingdao")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();

        // 设定发送的短信信息
        SendSmsRequest request = SendSmsRequest.builder()
                .phoneNumbers(phone)
                .signName("阿里云短信测试")
                .templateCode("SMS_154950909")
                .templateParam("{\"code\":\"" + validateCode + "\"}")
                .build();

        // 异步的发送 API 请求
        CompletableFuture<SendSmsResponse> response = client.sendSms(request);

        response.whenComplete((resp, ex) -> {
            try {
                if (ex != null) {
                    log.error("短信发送失败", ex);
                } else {
                    log.info("短信发送结果：{}", new Gson().toJson(resp));
                }
            } finally {
                client.close();  // 在回调完成后关闭客户端
            }
        });
    }


}
