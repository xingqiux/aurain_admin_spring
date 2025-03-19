package top.xkqq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.product.Brand;
import top.xkqq.entity.product.CategoryBrand;

import java.util.List;

@Mapper

public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {
    Page<CategoryBrand> findByPage(Page<CategoryBrand> categoryBrandPage, Long brandId, Long categoryId);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
