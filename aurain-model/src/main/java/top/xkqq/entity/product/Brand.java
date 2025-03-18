package top.xkqq.entity.product;

import lombok.Data;
import top.xkqq.entity.base.BaseEntity;

@Data
public class Brand extends BaseEntity {

    private String name;
    private String logo;

}