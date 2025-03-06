package top.xkqq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "aurain.auth")
public class UserAuthProperties {
    private List<String> noAuthUrls;
}
