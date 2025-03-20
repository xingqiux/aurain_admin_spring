package top.xkqq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.entity.product.ProductSpec;
import top.xkqq.service.ProductSpecService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}")
    @Schema(description = "根据分页参数查询商品参数信息")
    public Result<Page<ProductSpec>> findByPage(@PathVariable Integer page,
                                                @PathVariable Integer limit) {
        Page<ProductSpec> pageInfo = productSpecService.page(new Page<>(page, limit));
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @PutMapping("updateById")
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        productSpecService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.list();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }


}
