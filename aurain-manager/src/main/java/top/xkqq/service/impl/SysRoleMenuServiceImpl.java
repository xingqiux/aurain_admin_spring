package top.xkqq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.entity.system.SysMenu;
import top.xkqq.entity.system.SysRoleMenu;
import top.xkqq.mapper.SysRoleMenuServiceMapper;
import top.xkqq.service.SysMenuService;
import top.xkqq.service.SysRoleMenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {


    @Autowired
    // 用于获取角色当前的权限数据
    private SysRoleMenuServiceMapper sysRoleMenuServiceMapper;

    @Autowired
    // 用于获得全部的权限数据
    private SysMenuService sysMenuService;

    /**
     * 首先获得全部的权限菜单数据 ，然后根据 roleId 获取用户的数据，将两者同时用指定的标签存入 Map 中
     *
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        if (roleId == null) throw new RuntimeException("角色id不能为空");

        // 获取全部权限菜单数据
        List<SysMenu> sysMenuList = sysMenuService.findNodes();

        // 获取用户的权限菜单数据
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> roleMenuIds = sysRoleMenuServiceMapper.selectList(wrapper);

        // 将全部权限和用户权限全部存入 Map 中
        Map<String, Object> map = new HashMap<>();
        map.put("sysMenuList", sysMenuList);
        map.put("roleMenuIds", roleMenuIds);

        return map;

    }
}
