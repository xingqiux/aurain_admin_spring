package top.xkqq.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xkqq.entity.base.ProductUnit;
import top.xkqq.product.mapper.ProductUnitMapper;
import top.xkqq.product.service.ProductUnitService;

@Service
public class ProductUnitServiceImpl extends ServiceImpl<ProductUnitMapper, ProductUnit> implements ProductUnitService {

}