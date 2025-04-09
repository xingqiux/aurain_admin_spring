package top.xkqq.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.dto.CategoryBrandDto;
import top.xkqq.entity.product.Brand;
import top.xkqq.entity.product.CategoryBrand;
import top.xkqq.product.mapper.CategoryBrandMapper;
import top.xkqq.product.service.CategoryBrandService;

import java.util.List;

@Service

public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 根据传入的分页参数设置分页
     * 然后根据查询的配置进行多表查询
     *
     * @param page
     * @param limit
     * @param categoryBrandDto
     * @return
     */
    @Override
    public Page<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {

        // 设定分页参数
        Page<CategoryBrand> categoryBrandPage = new Page<>(page, limit);
        if (categoryBrandDto == null) {
            return categoryBrandMapper.findByPage(categoryBrandPage, null, null);
        }

        return categoryBrandMapper.findByPage(categoryBrandPage, categoryBrandDto.getBrandId(), categoryBrandDto.getCategoryId());
    }

    /**
     * 首先查找在 redis 中是否存在对应的数据，如果不存在再查找数据库，然后存入 redis 缓存
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {

        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
