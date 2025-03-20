package top.xkqq.entity.product;

import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
public class ProductDetails extends BaseEntity {

    private Long productId;
    private String imageUrls;

}