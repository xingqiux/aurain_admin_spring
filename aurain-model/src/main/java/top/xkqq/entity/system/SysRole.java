package top.xkqq.entity.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
@Schema(description = "SysRole")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "描述")
    private String description;

}