package top.xkqq.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.CategoryBrandDto;
import top.xkqq.entity.product.Brand;
import top.xkqq.entity.product.CategoryBrand;
import top.xkqq.service.CategoryBrandService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.List;

@RequestMapping("/admin/product/categoryBrand")
@RestController
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;


    @GetMapping("/{page}/{limit}")
    @Schema(description = "根据分页参数查询品牌信息")
    public Result<Page<CategoryBrand>> findByPage(@PathVariable Integer page,
                                                  @PathVariable Integer limit,
                                                  CategoryBrandDto categoryBrandDto) {
        Page<CategoryBrand> categoryBrandPage = categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(categoryBrandPage, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @PutMapping("updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        categoryBrandService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> brandList = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
