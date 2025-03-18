package top.xkqq.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

import java.util.List;

@Data
public class Category extends BaseEntity {
    private String name;
    private String imageUrl;
    private Long parentId;
    private Integer status;
    private Integer orderNum;

    @TableField(exist = false)
    private Boolean hasChildren;

    @TableField(exist = false)
    private List<Category> children;

}
