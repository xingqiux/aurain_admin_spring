package top.xkqq.entity.product;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

import java.math.BigDecimal;

@Data
public class ProductSku extends BaseEntity {

    private String skuCode;
    private String skuName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long productId;
    private String thumbImg;
    private BigDecimal salePrice;
    private BigDecimal marketPrice;
    private BigDecimal costPrice;
    private Integer stockNum;
    private Integer saleNum;
    private String skuSpec;
    private String weight;
    private String volume;
    private Integer status;

}