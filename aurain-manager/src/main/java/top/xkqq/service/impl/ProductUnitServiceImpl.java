package top.xkqq.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xkqq.entity.base.ProductUnit;
import top.xkqq.mapper.ProductUnitMapper;
import top.xkqq.service.ProductUnitService;

@Service
public class ProductUnitServiceImpl extends ServiceImpl<ProductUnitMapper, ProductUnit> implements ProductUnitService {

}