package top.xkqq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;

@Service
public interface SysRoleService  extends IService<SysRole> {
    public abstract PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer pageSize);

}
