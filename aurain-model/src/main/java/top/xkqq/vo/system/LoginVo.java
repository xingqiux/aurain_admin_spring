package top.xkqq.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用于封装登陆以后返回结果的响应数据
 */
@Data
@Schema(description = "登陆成功后响应实体类")
public class LoginVo {
    @Schema(description = "令牌")
    private String token;
    @Schema(description = "刷新令牌，可以为空")
    private String refreshToken;   //该字段不会存储对应值
}
