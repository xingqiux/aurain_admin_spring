package top.xkqq.entity.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.xkqq.entity.base.BaseEntity;

import java.io.Serial;


@Data
@Schema(description = "系统用户实体类")
public class SysUser{

  /**
   * 前端需要的返回值
   *     "userId": "string",        // 用户唯一标识
   *     "username": "string",      // 用户名
   *     "nickname": "string",      // 昵称
   *     "avatar": "string",        // 头像URL
   *     "roles": ["string"],       // 角色列表
   *     "permissions": ["string"]  // 权限列表
   */

  @Schema(description = "用户唯一标识")
  private String userId;
@Schema(description = "用户名")
  private String username;   //此字段的属性名与数据表字段不一致
  @Schema(description = "密码")
  private String password;
  @Schema(description = "昵称")
  private String nickname;
  @Schema(description = "手机号码")
  private String phone;
  @Schema(description = "头像 url")
  private String avatar;
  @Schema(description = "用户描述简介")
  private String description;
  @Schema(description = "用户状态")
  private long status;

}
