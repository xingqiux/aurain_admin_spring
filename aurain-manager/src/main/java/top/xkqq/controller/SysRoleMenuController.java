package top.xkqq.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.service.SysRoleMenuService;
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

}
