package top.xkqq.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "基础信息的类")
public class BaseEntity implements Serializable {


    // 此类中定义公共的属性
    @Schema(description = "唯一标识")
    @JsonFormat(shape = JsonFormat.Shape.STRING)    // 用于解决前端不能正确处理后端雪花算法id问题
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic(value = "0", delval = "1")
    @Schema(description = "是否删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

}
