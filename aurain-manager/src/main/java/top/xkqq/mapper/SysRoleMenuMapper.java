package top.xkqq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.xkqq.dto.AssginMenuDto;
import top.xkqq.entity.system.SysRoleMenu;


@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    void doAssign(AssginMenuDto assginRoleDto);

    void deleteByRoleId(Long roleId);
}
