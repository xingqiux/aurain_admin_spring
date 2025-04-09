package top.xkqq.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.entity.product.Product;
import top.xkqq.vo.product.ProductItemVo;

@Service
public interface ProductService extends IService<Product> {
    ProductItemVo item(Long skuId);
}
