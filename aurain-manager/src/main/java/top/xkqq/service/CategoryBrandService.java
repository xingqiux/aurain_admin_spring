package top.xkqq.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xkqq.dto.CategoryBrandDto;
import top.xkqq.entity.product.Brand;
import top.xkqq.entity.product.CategoryBrand;

import java.util.List;

public interface CategoryBrandService extends IService<CategoryBrand> {
    Page<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
