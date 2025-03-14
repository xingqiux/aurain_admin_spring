package top.xkqq.entity.system;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {
    private long roleId;
    private long menuId;
    private Integer isHalf;
}
