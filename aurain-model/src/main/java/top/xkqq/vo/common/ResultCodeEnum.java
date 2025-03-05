package top.xkqq.vo.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"操作成功"),
    LOGIN_ERROR(401,"用户名或密码错误"),
    CAPTCHA_ERROR(400,"验证码错误");

    private final Integer code; // 业务状态码
    private final String message;

    private ResultCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
