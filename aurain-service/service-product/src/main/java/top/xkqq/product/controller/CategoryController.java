package top.xkqq.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.entity.product.Category;
import top.xkqq.product.service.CategoryService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.List;

@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value = "/api/product/category")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获取分类树形数据")
    @GetMapping("findCategoryTree")
    public Result<List<Category>> findCategoryTree() {
        List<Category> list = categoryService.findCategoryTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}