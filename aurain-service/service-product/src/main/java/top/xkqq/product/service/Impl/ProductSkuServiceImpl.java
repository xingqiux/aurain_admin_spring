package top.xkqq.product.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import top.xkqq.dto.ProductSkuDto;
import top.xkqq.entity.product.ProductSku;
import top.xkqq.product.mapper.ProductSkuMapper;
import top.xkqq.product.service.ProductSkuService;
import top.xkqq.vo.common.PageResult;

import java.util.List;

@Service
@EnableCaching
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;


    @Override
    @Cacheable(value = "productSku")
    public List<ProductSku> findProductSkuBySale() {
        return productSkuMapper.findProductSkuBySale();
    }


    @Override
    public PageResult<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {

        List<ProductSku> pageList = productSkuMapper.findByPage(productSkuDto);

        return PageResult.fromPage(new Page<ProductSku>(page, limit).setRecords(pageList));

    }
}
