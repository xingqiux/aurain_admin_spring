package top.xkqq.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.dto.ProductDto;
import top.xkqq.entity.product.Product;

@Service
public interface ProductService extends IService<Product> {
    Page<Product> findByPage(Page<Product> productPage, ProductDto productDto);

    void saveProduct(Product product);

    Product getProductById(Long id);

    void updateProductById(Product product);

    void deleteProductById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}
