package top.xkqq.entity.system;


import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
public class SysRoleMenu extends BaseEntity {
    private long roleId;
    private long menuId;
}
