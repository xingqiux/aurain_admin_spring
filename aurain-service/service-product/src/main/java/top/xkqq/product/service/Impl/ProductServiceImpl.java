package top.xkqq.product.service.Impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.entity.product.Product;
import top.xkqq.entity.product.ProductDetails;
import top.xkqq.entity.product.ProductSku;
import top.xkqq.product.mapper.ProductDetailsMapper;
import top.xkqq.product.mapper.ProductMapper;
import top.xkqq.product.mapper.ProductSkuMapper;
import top.xkqq.product.service.ProductService;
import top.xkqq.vo.product.ProductItemVo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public ProductItemVo item(Long skuId) {

        // 获取 sku 信息
        ProductSku productSku = productSkuMapper.selectById(skuId);

        //当前商品信息
        Product product = productMapper.selectById(productSku.getProductId());

        //同一个商品下面的sku信息列表
        List<ProductSku> productSkuList = productSkuMapper.selectList(null);

        //建立sku规格与skuId对应关系
        //建立sku规格与skuId对应关系
        Map<String, Object> skuSpecValueMap = new HashMap<>();

        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });
        //商品详情信息
        ProductDetails productDetails = productDetailsMapper.selectById(productSku.getProductId());

        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);


        return productItemVo;

    }
}
