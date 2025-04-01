package top.xkqq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xkqq.entity.product.Brand;
import top.xkqq.service.BrandService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import xkqq.top.annotation.Log;
import xkqq.top.enums.OperatorType;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据页面编号和每页记录数获取品牌信息列表
     * 该方法使用GET请求来分页获取品牌信息，以便于前端分页展示
     *
     * @param page  页面编号，表示请求的品牌信息列表的页数
     * @param limit 每页记录数，表示每页品牌信息的数量
     * @return 返回包含分页品牌信息的Result对象，以及成功的结果码
     */
    @Log(title = "品牌列表", businessType = 0, operatorType = OperatorType.MANAGE)
    @GetMapping("/{page}/{limit}")
    public Result<Page<Brand>> findByPage(@PathVariable Integer page, @PathVariable Integer limit) {
        // 调用brandService的page方法进行分页查询，并按照Brand的id字段降序排序
        Page<Brand> brandPage = brandService.page(new Page<>(page, limit), new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getId));
        // 构建并返回成功的结果对象，包含查询到的分页品牌信息
        return Result.build(brandPage, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("updateById")
    public Result updateById(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        brandService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<Brand> list = brandService.list(new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getId));
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
