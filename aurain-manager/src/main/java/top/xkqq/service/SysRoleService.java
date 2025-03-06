package top.xkqq.service;


import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;


public interface SysRoleService  {
    public abstract PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer pageSize);
}
