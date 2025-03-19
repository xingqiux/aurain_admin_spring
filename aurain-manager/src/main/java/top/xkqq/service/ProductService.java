package top.xkqq.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.dto.ProductDto;
import top.xkqq.entity.product.Product;

@Service
public interface ProductService extends IService<Product> {
    Page<Product> findByPage(Page<Product> productPage, ProductDto productDto);
}
