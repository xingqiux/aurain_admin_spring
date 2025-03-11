package top.xkqq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserDto {
    @Schema(description = "关键字")
    private String keyword;
    @Schema(description = "用户创建的开始时间")
    private String createTimeBegin;
    @Schema(description = "用户创建的结束时间")
    private String createTimeEnd;


}
