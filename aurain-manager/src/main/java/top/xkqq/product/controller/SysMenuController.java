package top.xkqq.product.controller;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.entity.system.SysMenu;
import top.xkqq.product.service.SysMenuService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.List;

@RequestMapping("/admin/system/sysMenu")
@RestController
@Tag(name = "菜单管理")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/findNodes")
    @Schema(description = "获取菜单列表")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取表单进行提交，在后端需要将提交过来的表单数据保存到数据库中
     */
    @PostMapping("/save")
    @Schema(description = "添加菜单")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改菜单的相关信息
     *
     * @param sysMenu
     * @return
     */
    @Schema(description = "修改菜单")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除菜单相关信息
     */
    @Schema(description = "删除菜单")
    @DeleteMapping("/removeById/{id}")
    public Result delete(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
