package top.xkqq.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "验证码响应结果实体类")
public class ValidateCodeVo {

    @Schema(description = "验证码的 key")
    private String captchaKey;  //验证码在图片缓存的唯一标识
    @Schema(description = "验证码的 value")
    private String captchaBase64;   //验证码图片的 Base64 编码
}

