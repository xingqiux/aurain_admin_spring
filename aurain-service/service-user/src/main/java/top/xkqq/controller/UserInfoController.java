package top.xkqq.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.UserLoginDto;
import top.xkqq.dto.UserRegisterDto;
import top.xkqq.entity.user.UserInfo;
import top.xkqq.service.UserInfoService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.user.UserInfoVo;

@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "会员注册")
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员登录")
    @PostMapping("login")
    public Result login(UserLoginDto userLoginDto) {
        return Result.build(userInfoService.login(userLoginDto), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo info = userInfoService.getCurrentUserInfo(token);

        return Result.build(info, ResultCodeEnum.SUCCESS);
    }

}
