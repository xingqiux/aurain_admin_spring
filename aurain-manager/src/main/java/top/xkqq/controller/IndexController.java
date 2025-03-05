package top.xkqq.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.LoginDto;
import top.xkqq.service.SysUserService;
import top.xkqq.service.ValidateCodeService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.system.LoginVo;
import top.xkqq.vo.system.ValidateCodeVo;

@Tag(name = " 用户接口")
@RestController
@RequestMapping(value = "/api/v1/auth")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;


    /**
     * 实现验证码接口
     */
    @Operation(summary = "获取验证码方法captcha")
    @GetMapping("captcha")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }


    /**
     *  Post 方式 返回 Result
     *  参数为 LoginDto
     *  调用 service 层传递 LoginDto
     *  返回 LoginVo (如果 Vo 为空，说明用户不存在或密码错误，否则返回数据)
     *  return Result添加LoginVo
     */
    @Operation(summary = "登录方法login")
    @PostMapping("login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);

        return Result.build(loginVo,ResultCodeEnum.SUCCESS);
    }

}
