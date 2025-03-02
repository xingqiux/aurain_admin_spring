package top.xkqq.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.dto.LoginDto;
import top.xkqq.entity.system.SysUser;
import top.xkqq.service.SysUserService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.system.LoginVo;

@Tag(name = " 用户接口")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     *  Post 方式 返回 Result
     *  参数为 LoginDto
     *  调用 service 层传递 LoginDto
     *  返回 LoginVo
     *  return Result添加LoginVo
     */
    @Operation(summary = "登录方法login")
    @PostMapping("login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);

        if (loginVo ==null){
            return Result.build(null, ResultCodeEnum.LOGIN_ERROR);
        }
        return Result.build(loginVo,ResultCodeEnum.SUCCESS);
    }

}
