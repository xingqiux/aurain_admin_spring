package top.xkqq.common.exception;


import lombok.Data;
import top.xkqq.vo.common.ResultCodeEnum;

/**
 * 自定义异常处理
 */
@Data
public class AurainException extends RuntimeException{

    private Integer code;   //状态码
    private String message; // 错误信息

    private ResultCodeEnum resultCodeEnum; // 封装的状态码和错误信息

    // 构造类
    public AurainException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public AurainException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.resultCodeEnum=resultCodeEnum;
    }
}
