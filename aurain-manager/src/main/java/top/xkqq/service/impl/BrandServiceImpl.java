package top.xkqq.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xkqq.entity.product.Brand;
import top.xkqq.mapper.BrandMapper;
import top.xkqq.service.BrandService;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
}
