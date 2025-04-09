package top.xkqq.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.entity.product.Category;

import java.util.List;

@Service
public interface CategoryService extends IService<Category> {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}
