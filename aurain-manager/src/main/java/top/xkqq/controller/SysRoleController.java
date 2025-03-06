package top.xkqq.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;
import top.xkqq.service.SysRoleService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.Objects;

@Tag(name = " 分组接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @Operation(summary = "访问分页的findByPage")
    @PostMapping("/findByPage/{current}/{pageSize}")
    public Result<SysRole> findByPage(@PathVariable("current") Integer current,
                             @PathVariable("pageSize") Integer pageSize,
                             @RequestBody SysRoleDto sysRoleDto){
        System.out.println(sysRoleDto);
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto ,current , pageSize) ;
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);


    }

}
