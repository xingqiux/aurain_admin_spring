package top.xkqq.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;
import top.xkqq.service.SysRoleService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

@Tag(name = " 分组接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @Operation(summary = "访问分页 findByPage")
    @PostMapping("/findByPage/{current}/{pageSize}")
    public Result<SysRole> findByPage(@PathVariable("current") Integer current,
                             @PathVariable("pageSize") Integer pageSize,
                             @RequestBody SysRoleDto sysRoleDto){
        System.out.println(sysRoleDto);

        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto ,current , pageSize) ;
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加权限 saveSysRole")
    @PostMapping("/saveSysRole")
    public Result<SysRole> saveSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.build(sysRole, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改权限 updateSysRole")
    @PutMapping("/updateSysRole")
    public Result<SysRole> updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.build(sysRole, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除权限用户 deleteSysRole")
    @DeleteMapping("/deleteById/{id}")
    public Result<SysRole> deleteSysRole(@PathVariable("id") Long id) {
        System.out.println(id);
        boolean b = sysRoleService.removeById(id);
        System.out.println("b = " + b);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
