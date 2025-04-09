package top.xkqq.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.xkqq.entity.system.SysRoleUser;

@Mapper
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {
    void doAssign(@Param("userId") Long userId,
                  @Param("roleId") Long roleId);        // 添加关联关系
}
