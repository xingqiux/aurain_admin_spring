package top.xkqq.entity.product;


import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
public class ProductSpec extends BaseEntity {
    private String specName;
    private String specValue;
}
