package top.xkqq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户请求提交参数")
public class LoginDto {

    @Schema(description = "用户名")
//    @JsonProperty("username")  // 指定JSON中的字段名
    private String userName;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "提交验证码")
    private String captcha;
    @Schema(description = "验证码key")
    private String codeKey;

}
