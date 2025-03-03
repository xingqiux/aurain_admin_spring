package top.xkqq.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用于封装登陆以后返回结果的响应数据
 */
@Data
@Schema(description = "登陆成功后响应实体类")
public class LoginVo {
//    @Schema(description = "令牌")
//    private String token;
//    @Schema(description = "刷新令牌，可以为空")
//    private String refreshToken;   //该字段不会存储对应值

    /**
     * 返回符合前端要求的参数
     *   {
     *     "accessToken": "string",    // JWT访问令牌
     *     "refreshToken": "string",   // 刷新令牌
     *     "tokenType": "string",      // 令牌类型，通常为"Bearer"
     *     "expiresIn": number         // 访问令牌有效期(秒)
     *   }
     */
    @Schema(description = "JWT访问令牌")
    private String accessToken;
    @Schema(description = "刷新令牌")
    private String refreshToken;
    @Schema(description = "令牌类型，通常为\"Bearer")
    private String tokenType;
    @Schema(description = "访问令牌有效期(秒)")
    private Integer expiresIn;

}
