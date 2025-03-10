package top.xkqq.mapper;


import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;

import java.util.List;

@Component
public interface SysRoleMapper extends  BaseMapper<SysRole>{
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
}
