package top.xkqq.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.dto.ProductDto;
import top.xkqq.entity.product.Product;
import top.xkqq.mapper.ProductMapper;
import top.xkqq.service.ProductService;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<Product> findByPage(Page<Product> productPage, ProductDto productDto) {
        return productMapper.findByPage(productPage, productDto.getBrandId(), productDto.getCategory1Id(), productDto.getCategory2Id(), productDto.getCategory3Id());
    }
}
