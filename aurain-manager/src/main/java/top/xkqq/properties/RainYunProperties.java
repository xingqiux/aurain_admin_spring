package top.xkqq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aurain.rainyun")
public class RainYunProperties {
    private String endPointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
