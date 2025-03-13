package top.xkqq.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
}
