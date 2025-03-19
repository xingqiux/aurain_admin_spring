package top.xkqq.entity.product;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
public class Product extends BaseEntity {

    private String name;                    // 商品名称
    private Long brandId;                    // 品牌ID
    private Long category1Id;                // 一级分类id
    private Long category2Id;                // 二级分类id
    private Long category3Id;                // 三级分类id
    private String unitName;                // 计量单位
    private String sliderUrls;                // 轮播图
    private String specValue;                // 商品规格值json串
    private Integer status;                    // 线上状态：0-初始值，1-上架，-1-自主下架
    private Integer auditStatus;            // 审核状态
    private String auditMessage;            // 审核信息

    // 扩展的属性，用来封装响应的数据
    @TableField(exist = false)
    private String brandName;                // 品牌
    @TableField(exist = false)
    private String category1Name;            // 一级分类
    @TableField(exist = false)
    private String category2Name;            // 二级分类
    @TableField(exist = false)
    private String category3Name;            // 三级分类

}
