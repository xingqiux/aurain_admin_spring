package top.xkqq.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

/**
 * 全局异常方式是会走此类写的 handler
 */
@RestControllerAdvice   //直接返回 json 字符串
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> error(RuntimeException e){
        return Result.build(null,500,"服务器内部错误");
    }

    // 自定义异常处理
    @ExceptionHandler(AurainException.class)
    public Result<Object> error(AurainException aurainException){
        aurainException.printStackTrace(); //自定义异常需要手动抛出
        return Result.build(null, aurainException.getResultCodeEnum());
    }

}
