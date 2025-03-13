package top.xkqq.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.entity.system.SysMenu;
import top.xkqq.helper.MenuHelper;
import top.xkqq.mapper.SysMenuMapper;
import top.xkqq.service.SysMenuService;

import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

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

//        System.out.println("sysUsers = " + sysMenuList);

        if (CollectionUtil.isEmpty(sysMenuList)) return null;

        // 构建树状数据
        List<SysMenu> sysMenusTree = MenuHelper.buildTree(sysMenuList);
        return sysMenusTree;


    }
}
