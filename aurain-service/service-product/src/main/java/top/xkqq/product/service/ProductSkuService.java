package top.xkqq.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.dto.ProductSkuDto;
import top.xkqq.entity.product.ProductSku;
import top.xkqq.vo.common.PageResult;

import java.util.List;

@Service
public interface ProductSkuService extends IService<ProductSku> {

    List<ProductSku> findProductSkuBySale();

    PageResult<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);
}

