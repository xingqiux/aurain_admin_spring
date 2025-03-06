package top.xkqq.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.LoginDto;
import top.xkqq.entity.system.SysUser;
import top.xkqq.service.SysUserService;
import top.xkqq.service.ValidateCodeService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.system.LoginVo;
import top.xkqq.vo.system.ValidateCodeVo;

@Tag(name = " 用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;


    /**
     * 用户退出接口
     * 用于实现用户退出功能
     * 1. 获取请求头中的 token
     * 2. 根据 token 删除 redis 中的 token
     * 4. 返回结果
     * @return
     */
    @Operation(summary = "用户退出方法logout")
    @GetMapping("logout")
    public Result logout(@RequestHeader("token") String token){
        // 获取请求头中的 token
        // 根据 token 获取用户信息
        sysUserService.logout(token);
        // 返回结果
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    /**
     * 实现获取用户信息的接口
     * 1. 根据接收参数获取 token
     * 2. 根据 token 获取用户信息
     * 3. 用户信息返回
     */
    @Operation(summary = "获取用户信息方法getUserInfo")
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader("token") String token){
        System.out.println(token);

        //根据 token 获取用户信息
        SysUser userInfo = sysUserService.getUserInfo(token);
        return Result.build(userInfo,ResultCodeEnum.SUCCESS);
    }
    /**
     * 实现验证码接口
     */
    @Operation(summary = "获取验证码方法captcha")
    @GetMapping("generateValidateCode")
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
        System.out.println(loginDto);
        LoginVo loginVo = sysUserService.login(loginDto);

        return Result.build(loginVo,ResultCodeEnum.SUCCESS);
    }

}
