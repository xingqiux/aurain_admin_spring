package top.xkqq.mapper;


import org.springframework.stereotype.Component;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;

import java.util.List;

@Component
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
}
