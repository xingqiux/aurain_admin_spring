package top.xkqq.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

// 权限角色与用户关联表
@Data
@TableName("sys_user_role")
public class SysRoleUser extends BaseEntity {
    private Long roleId;       // 角色id
    private Long userId;       // 用户id

}
