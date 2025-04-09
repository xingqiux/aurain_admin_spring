package top.xkqq.vo.h5;

import lombok.Data;
import top.xkqq.entity.product.Category;
import top.xkqq.entity.product.ProductSku;

import java.util.List;

@Data
public class IndexVo {

    private List<ProductSku> productSkuList;
    private List<Category> categoryList;

}
