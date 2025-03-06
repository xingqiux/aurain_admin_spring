package top.xkqq.service.impl;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.xkqq.service.ValidateCodeService;
import top.xkqq.vo.system.ValidateCodeVo;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成验证码流程
     * 生成图片验证码数据 使用(hutool)
     * 把验证数据存入到 redis 中 并设置过期时间
     * 返回验证码的 key 和 验证码图片数据
     * @return
     */
    @Override
    public ValidateCodeVo generateValidateCode() {
        //1. 使用图片验证码生成工具生成图片验证码 hutool
        //参数：宽度，高度，验证码位数，干扰元素个数
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String captchaValue = circleCaptcha.getCode();
        String captchaBase64 = circleCaptcha.getImageBase64();

        //2. 把验证码数据存入到 redis 中 并设置过期时间
        //使用 UUID 作为验证码的 key
        String codeKey = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:validate"+codeKey,captchaValue,5, TimeUnit.MINUTES);

        System.out.println(captchaValue);
        // 3. 返回数据
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue(captchaBase64);

        return validateCodeVo;


    }
}
