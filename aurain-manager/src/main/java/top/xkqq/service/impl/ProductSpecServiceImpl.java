package top.xkqq.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xkqq.entity.product.ProductSpec;
import top.xkqq.mapper.ProductSpecMapper;
import top.xkqq.service.ProductSpecService;

@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {
}
