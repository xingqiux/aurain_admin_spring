package top.xkqq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aurain.alibaba.sms")
public class SmsProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
