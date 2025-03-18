package top.xkqq.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
public class CategoryBrand extends BaseEntity {

    private Long brandId;
    private Long categoryId;

    // 扩展的属性用来封装前端所需要的数据
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String brandName;
    @TableField(exist = false)
    private String logo;

}

