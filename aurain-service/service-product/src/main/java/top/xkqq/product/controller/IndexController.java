package top.xkqq.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.entity.product.Category;
import top.xkqq.entity.product.ProductSku;
import top.xkqq.product.service.CategoryService;
import top.xkqq.product.service.ProductSkuService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.h5.IndexVo;

import java.util.List;


@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {

    @Autowired
    private ProductSkuService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "获取首页数据")
    public Result<IndexVo> findData() {
        List<ProductSku> productSkuList = productService.findProductSkuBySale();
        List<Category> categoryList = categoryService.findOneCategory();

        IndexVo indexVo = new IndexVo();
        indexVo.setProductSkuList(productSkuList);
        indexVo.setCategoryList(categoryList);

        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }

}
