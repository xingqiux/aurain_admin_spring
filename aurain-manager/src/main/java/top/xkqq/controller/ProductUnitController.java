package top.xkqq.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.entity.base.ProductUnit;
import top.xkqq.service.ProductUnitService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {

    @Autowired
    private ProductUnitService productUnitService;


    @GetMapping("findAll")
    public Result<List<ProductUnit>> findAll() {
        return Result.build(productUnitService.list(), ResultCodeEnum.SUCCESS);
    }
}
