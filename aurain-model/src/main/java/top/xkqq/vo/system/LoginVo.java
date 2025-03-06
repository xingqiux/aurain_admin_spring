package top.xkqq.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用于封装登陆以后返回结果的响应数据
 */
@Data
@Schema(description = "登陆成功后响应实体类")
public class LoginVo {

    @Schema(description = "JWT访问令牌")
    private String token ;
    @Schema(description = "刷新令牌")
    private String refresh_token ;		// 该字段不会存储对应的值

}
