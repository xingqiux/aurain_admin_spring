package top.xkqq.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

import java.util.List;

@Data
@Schema(description = "菜单表")
public class SysMenu extends BaseEntity {
    private Long parentId;
    private String title;
    private String component;
    private Integer sortValue;
    private Integer status;

    @Schema(description = "下级列表")
    @TableField(exist = false)
    private List<SysMenu> children;
}

