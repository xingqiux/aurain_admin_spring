package top.xkqq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xkqq.dto.AssginMenuDto;
import top.xkqq.entity.system.SysMenu;
import top.xkqq.entity.system.SysRoleMenu;
import top.xkqq.mapper.SysRoleMenuMapper;
import top.xkqq.service.SysMenuService;
import top.xkqq.service.SysRoleMenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {


    @Autowired
    // 用于获取角色当前的权限数据
    private SysRoleMenuMapper sysRoleMenuMapper;

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
        // 这里他妈的要设置父节点的半查询，否则会出现前端所有都选中
        wrapper.eq(SysRoleMenu::getIsHalf, 0);
        List<SysRoleMenu> roleMenuIds = sysRoleMenuMapper.selectList(wrapper);

        // 将全部权限和用户权限全部存入 Map 中
        Map<String, Object> map = new HashMap<>();
        map.put("sysMenuList", sysMenuList);
        map.put("roleMenuIds", roleMenuIds);

        return map;

    }

    /**
     * 将角色设定的权限保存
     * <p>
     * 先清除原先的数据，然后存储现在的设定的数据
     *
     * @param assginRoleDto
     */
    @Override
    @Transactional
    public void doAssign(AssginMenuDto assginRoleDto) {
        if (assginRoleDto.getRoleId() == null) throw new RuntimeException("角色id不能为空");
        // 删除原先的数据
        sysRoleMenuMapper.deleteByRoleId(assginRoleDto.getRoleId());

        // 将携带的现有数据存储
        List<Map<String, Number>> menuIdList = assginRoleDto.getMenuIdList();

        // 判断这个列表中的值是否合法，然后进行批量插入操作
        if (menuIdList != null && menuIdList.size() > 0) {
            sysRoleMenuMapper.doAssign(assginRoleDto);
        }
    }
}
