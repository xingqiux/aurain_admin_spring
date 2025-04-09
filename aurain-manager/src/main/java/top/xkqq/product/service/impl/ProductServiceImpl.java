package top.xkqq.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xkqq.dto.ProductDto;
import top.xkqq.entity.product.Product;
import top.xkqq.entity.product.ProductDetails;
import top.xkqq.entity.product.ProductSku;
import top.xkqq.product.mapper.ProductDetailsMapper;
import top.xkqq.product.mapper.ProductMapper;
import top.xkqq.product.mapper.ProductSkuMapper;
import top.xkqq.product.service.ProductService;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public Page<Product> findByPage(Page<Product> productPage, ProductDto productDto) {
        return productMapper.findByPage(productPage, productDto.getBrandId(), productDto.getCategory1Id(), productDto.getCategory2Id(), productDto.getCategory3Id());
    }


    @Override
    @Transactional
    /**
     * 保存产品信息，包括产品基本信息、轮播图信息和SKU信息
     * 此方法使用了事务注解，确保所有数据库操作在同一事务中完成
     *
     * @param product 要保存的产品对象，包含产品基本信息、轮播图信息和SKU信息
     */
    public void saveProduct(Product product) {

        // 设置上架审核初始值
        product.setStatus(0);
        product.setAuditStatus(0);

        // 保存实体
        this.save(product);

        //提取轮播图信息和 sku 信息分别存储
        // 轮播图信息
        String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsMapper.insert(productDetails);

        // sku 信息存储
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0, size = productSkuList.size(); i < size; i++) {
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "-" + i);
            productSku.setProductId(product.getId());
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);
            productSkuMapper.insert(productSku);                    // 保存数据
        }
    }

    @Override

    public Product getProductById(Long id) {

        Product product = productMapper.getProductById(id);

        // 根据商品的id查询sku数据
        List<ProductSku> productSkuList = productSkuMapper.selectList(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id));

        log.info("productSkuList:{}", productSkuList);

        product.setProductSkuList(productSkuList);

        // 根据商品的id查询商品详情数据
        ProductDetails productDetails = productDetailsMapper.selectOne(new LambdaQueryWrapper<ProductDetails>().eq(ProductDetails::getProductId, id));
        product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;

    }

    @Override
    @Transactional
    public void updateProductById(Product product) {
        // 修改 ProductDetails  的相关信息
        ProductDetails productDetails = new ProductDetails();
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetails.setProductId(product.getId());

        productDetailsMapper.update(
                productDetails,
                Wrappers.<ProductDetails>lambdaUpdate()
                        .eq(ProductDetails::getProductId, product.getId())
        );


        product.getProductSkuList().forEach(sku -> {
            productSkuMapper.update(
                    sku,  // 需要确保sku对象包含要更新的字段值
                    Wrappers.<ProductSku>lambdaUpdate()
                            .eq(ProductSku::getId, sku.getId())       // 根据SKU主键更新
                            .eq(ProductSku::getProductId, product.getId()) // 安全校验
            );
        });
        // 更新Product主表
        productMapper.updateById(product);  // 直接使用id更新更简洁
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        // 删除 ProductDetails  的相关信息
        productDetailsMapper.delete(Wrappers.<ProductDetails>lambdaQuery().eq(ProductDetails::getProductId, id));
        productSkuMapper.delete(Wrappers.<ProductSku>lambdaQuery().eq(ProductSku::getProductId, id));
        productMapper.deleteById(id);
    }


    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if (auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if (status == 1) {
            // 上架状态
            product.setStatus(1);
        } else {
            // 下架状态
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
