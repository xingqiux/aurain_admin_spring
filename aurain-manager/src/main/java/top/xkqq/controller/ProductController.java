package top.xkqq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
