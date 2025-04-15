package top.xkqq.vo.product;

import com.alibaba.fastjson.JSONArray;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.xkqq.entity.product.Product;
import top.xkqq.entity.product.ProductSku;

import java.util.List;
import java.util.Map;

@Data
@Schema(description = "商品详情对象")
public class ProductItemVo {

    @Schema(description = "商品sku信息")
    private ProductSku productSku;

    @Schema(description = "商品信息")
    private Product product;

    @Schema(description = "商品轮播图列表")
    private List<String> sliderUrlList;

    @Schema(description = "商品详情图片列表")
    private List<String> detailsImageUrlList;

    @Schema(description = "商品规格信息")
    private JSONArray specValueList;

    @Schema(description = "商品规格对应商品skuId信息")
    private Map<String, String> skuSpecValueMap;

}