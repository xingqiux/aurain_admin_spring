package top.xkqq.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;

import java.util.Map;

@Service
public interface SysRoleService  extends IService<SysRole> {
    Page<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer pageSize);

    Map<String, Object> findAllRoles(String userId);
}
