package top.xkqq.product.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xkqq.entity.product.Brand;
import top.xkqq.product.mapper.BrandMapper;
import top.xkqq.product.service.BrandService;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
}
