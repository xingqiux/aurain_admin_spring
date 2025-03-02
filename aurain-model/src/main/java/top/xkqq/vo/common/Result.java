package top.xkqq.vo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Result<T> {
    //返回码
    @Schema(description = "业务状态码")
    private int code;

    //返回消息
    @Schema(description = "响应信息")
    private String message;

    //返回数据
    @Schema(description = "业务数据")
    private T data;

    //私有化构造
    private Result(){}

    //返回数据
    public static <T> Result<T> build(T body,Integer code,String message){
        Result<T> result = new Result<>();
        result.setData(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    // 通过枚举构造 Result 对象
    public static <T> Result build(T body,ResultCodeEnum resultCodeEnum){
        return build(body,resultCodeEnum.getCode(),resultCodeEnum.getMessage());
    }
}
