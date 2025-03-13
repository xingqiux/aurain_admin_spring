package top.xkqq.helper;

import top.xkqq.entity.system.SysMenu;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MenuHelper {

    // 构建树形菜单入口方法
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        // 1. 使用 Stream API 处理菜单列表
        return sysMenuList.stream()
                // 2. 过滤出所有根节点（parent_id=0）
                .filter(menu -> menu.getParentId().longValue() == 0)

                .sorted(Comparator.comparingInt(SysMenu::getSortValue))

                // 3. 对每个根节点递归构建子树
                .map(root -> findChildren(root, sysMenuList))
                // 4. 将处理后的流收集为 List
                .collect(Collectors.toList());
    }

    // 递归构建子树
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        // 1. 使用 Stream API 处理当前节点的子节点
        List<SysMenu> children = treeNodes.stream()
                .filter(child -> child.getParentId().equals(sysMenu.getId()))       // 2. 过滤出父节点为当前节点 ID 的子节点
                .sorted(Comparator.comparingInt(SysMenu::getSortValue))// 3. 按 sort_value 升序排序（如果 SQL 未排序，此处生效）
                .map(child -> findChildren(child, treeNodes))   // 4. 递归构建每个子节点的子树
                .collect(Collectors.toList());    // 5. 将子节点流收集为 List

        // 6. 将子节点列表设置到当前节点
        sysMenu.setChildren(children);
        return sysMenu;
    }
}