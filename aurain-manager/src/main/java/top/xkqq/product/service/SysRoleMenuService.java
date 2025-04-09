package top.xkqq.product.service;

import org.springframework.stereotype.Service;
import top.xkqq.dto.AssginMenuDto;

import java.util.Map;

@Service
public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginRoleDto);
}
