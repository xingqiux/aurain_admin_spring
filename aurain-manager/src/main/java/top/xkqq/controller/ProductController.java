package top.xkqq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.dto.ProductDto;
import top.xkqq.entity.product.Product;
import top.xkqq.service.ProductService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

@RestController
@RequestMapping("/admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{page}/{limit}")
    public Result<Page<Product>> findByPage(@PathVariable Integer page,
                                            @PathVariable Integer limit,
                                            ProductDto productDto) {
        Page<Product> productPage = new Page<>(page, limit);
        Page<Product> pageInfo = productService.findByPage(productPage, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.saveProduct(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据 id 查询相关信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新产品信息
     * <p>
     * 通过产品ID更新产品信息此方法使用Put请求映射到/updateById路径
     * 主要用于更新现有产品的详细信息要求提供完整的产品信息作为请求体
     *
     * @param product 产品实体类，包含产品各项属性，用于更新产品信息
     * @return 返回更新结果，如果更新成功，返回成功结果码
     */
    @PutMapping("/updateById")
    public Result updateById(@Parameter(name = "product", description = "请求参数实体类", required = true) @RequestBody Product product) {
        productService.updateProductById(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable Long id) {
        productService.deleteProductById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新审批状态
     *
     * @param id
     * @param auditStatus
     * @return
     */
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id, @PathVariable Integer auditStatus) {
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 设置上下架状态
     *
     * @param id
     * @param status
     * @return
     */
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        productService.updateStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
