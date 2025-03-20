package top.xkqq.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xkqq.entity.system.SysMenu;
import top.xkqq.entity.system.SysUser;
import top.xkqq.helper.MenuHelper;
import top.xkqq.mapper.SysMenuMapper;
import top.xkqq.mapper.SysRoleMenuMapper;
import top.xkqq.service.SysMenuService;
import top.xkqq.util.AuthContextUtil;
import top.xkqq.vo.system.SysMenuVo;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 1. 查询到所有的 SysMenu 数据
     * 2. 判断数据是否为空
     * 3. 如果数据库数据不为空，进行树状处理
     *
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        // 查询到所有的 SysMenu 数据
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();

        // 设置 wrapper 查询条件 排序
        wrapper.orderBy(true, true, SysMenu::getId, SysMenu::getSortValue);
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(wrapper);

        if (CollectionUtil.isEmpty(sysMenuList)) return null;

        // 构建树状数据
        List<SysMenu> sysMenusTree = MenuHelper.buildTree(sysMenuList);
        return sysMenusTree;


    }

    @Override
    public List<SysMenuVo> findUserMenuList() {
        SysUser sysUser = AuthContextUtil.get();
        // 获取当前用户的 id
        Long userId = sysUser.getId();

        List<SysMenu> sysMenus = sysMenuMapper.selectListByUserId(userId);

        // 构建树形数据
        List<SysMenu> sysMenuVos = MenuHelper.buildTree(sysMenus);
        return buildMenus(sysMenuVos);
    }

    /**
     * 将List<SysMenu>对象转换成List<SysMenuVo>对象
     * 该方法主要用于构建菜单列表，将系统菜单数据转换为菜单视图对象列表
     *
     * @param menus 菜单列表，包含SysMenu对象的列表
     * @return 转换后的菜单视图列表，包含SysMenuVo对象的列表
     */
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {
        // 创建一个LinkedList来存储转换后的SysMenuVo对象
        LinkedList<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        // 遍历传入的SysMenu对象列表
        for (SysMenu sysMenu : menus) {
            // 创建一个新的SysMenuVo对象来存储转换后的菜单信息
            SysMenuVo sysMenuVo = new SysMenuVo();
            // 设置菜单标题，这里应从sysMenu对象中获取
            sysMenuVo.setTitle(sysMenu.getTitle());
            // 设置菜单名称，这里应从sysMenu对象中获取
            sysMenuVo.setName(sysMenu.getComponent());
            // 获取当前菜单的子菜单列表
            List<SysMenu> children = sysMenu.getChildren();
            // 如果子菜单列表不为空，则递归调用buildMenus方法来构建子菜单列表
            if (!CollectionUtil.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            // 将转换后的SysMenuVo对象添加到列表中
            sysMenuVoList.add(sysMenuVo);
        }
        // 返回构建完成的菜单视图列表
        return sysMenuVoList;
    }

    @Transactional
    @Override
    public boolean save(SysMenu sysMenu) {

        // 添加新的节点
        sysMenuMapper.insert(sysMenu);

        // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenuIsHalf(sysMenu);
        return true;
    }

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {

        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());

        if (parentMenu != null) {

            // 将该id的菜单设置为半开

            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());

            // 递归调用
            updateSysRoleMenuIsHalf(parentMenu);

        }

    }

}
