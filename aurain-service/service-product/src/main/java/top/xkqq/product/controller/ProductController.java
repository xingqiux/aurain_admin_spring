package top.xkqq.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.dto.ProductSkuDto;
import top.xkqq.entity.product.ProductSku;
import top.xkqq.product.service.ProductService;
import top.xkqq.product.service.ProductSkuService;
import top.xkqq.vo.common.PageResult;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.product.ProductItemVo;

@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value = "/api/product")
@SuppressWarnings({"unchecked", "rawtypes"})
@EnableCaching
public class ProductController {

    @Autowired
    private ProductService productService;


    @Autowired
    private ProductSkuService productSkuService;

    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    @Cacheable(value = "productSku")
    public Result<PageResult<ProductSku>> findByPage(@Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,
                                                     @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
                                                     @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {
        PageResult<ProductSku> pageResult = productSkuService.findByPage(page, limit, productSkuDto);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result<ProductItemVo> item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

}
