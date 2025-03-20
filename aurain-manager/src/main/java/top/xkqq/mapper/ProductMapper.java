package top.xkqq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.product.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    Page<Product> findByPage(Page<Product> productPage, Long brandId, Long category1Id, Long category2Id, Long category3Id);

    Product getProductById(Long id);
}
