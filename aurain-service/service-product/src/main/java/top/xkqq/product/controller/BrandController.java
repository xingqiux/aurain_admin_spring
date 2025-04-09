package top.xkqq.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.entity.product.Brand;
import top.xkqq.product.service.BrandService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.List;

@RestController
@Tag(name = "品牌管理")
@SuppressWarnings({"unchecked", "rawtypes"})
// 修正后代码
@RequestMapping("/api/product/brand/")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "获取全部品牌")
    @GetMapping("findAll")
    public Result<List<Brand>> findAll() {
        return Result.build(brandService.list(), ResultCodeEnum.SUCCESS);
    }

}

