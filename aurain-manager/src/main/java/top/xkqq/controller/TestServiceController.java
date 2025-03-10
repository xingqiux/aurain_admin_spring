package top.xkqq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;
import top.xkqq.service.SysRoleService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

@Tag(name = "用于测试的接口")
@RestController
@RequestMapping("/test")
public class TestServiceController {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/service")
    public Result<SysRole> testSysRoleService(){
        SysRoleDto sysRoleDto = new SysRoleDto();
        sysRoleDto.setRoleName("");

        // 设置分页参数
        Page<SysRole> sysRolePage = new Page<>(1, 10);

        // 设置查询条件
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SysRole::getRoleName, sysRoleDto.getRoleName());

        Page<SysRole> page = sysRoleService.page(sysRolePage, queryWrapper);

        return Result.build(page, ResultCodeEnum.SUCCESS);
    }

}
