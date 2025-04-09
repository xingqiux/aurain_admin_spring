package top.xkqq.product.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.AssginMenuDto;
import top.xkqq.product.service.SysRoleMenuService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    @Schema(description = "根据角色id查询功能菜单")
    public Result<Map<String, Object>> findSysRoleMenuByRoleId(@PathVariable Long roleId) {
        Map<String, Object> sysRoleMenuMap = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(sysRoleMenuMap, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/doAssign")
    @Schema(description = "为角色分配功能菜单")
    public Result doAssign(@RequestBody AssginMenuDto assginRoleDto) {

        // 调用调用服务层，先删除原先的权限，然后写上 assginRoleDto 的权限新权限列表
        sysRoleMenuService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }

}
