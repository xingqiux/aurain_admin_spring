package top.xkqq.entity.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.xkqq.entity.base.BaseEntity;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)  //@EqualsAndHashCode(callSuper = true) 表示在生成 equals 和 hashCode 方法时，会考虑父类（即 BaseEntity）的字段。
@Data
@Schema(description = "系统用户实体类")
public class SysUser extends BaseEntity {

@Schema(description = "用户名")
  private String userName;   //此字段的属性名与数据表字段不一致
  @Schema(description = "密码")
  private String password;
  @Schema(description = "昵称")
  private String name;
  @Schema(description = "手机号码")
  private String phone;
  @Schema(description = "头像 url")
  private String avatar;
  @Schema(description = "用户描述简介")
  private String description;
  @Schema(description = "用户状态")
  private long status;

}
