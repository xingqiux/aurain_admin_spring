package top.xkqq.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.product.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
