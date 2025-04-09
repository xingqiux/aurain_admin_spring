package top.xkqq.product.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.xkqq.entity.product.Category;
import top.xkqq.product.mapper.CategoryMapper;
import top.xkqq.product.service.CategoryService;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableCaching
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Cacheable(value = "category", key = "'one'")
    public List<Category> findOneCategory() {

        // 从Redis缓存中查询所有的一级分类数据
        String categoryListJSON = (String) redisTemplate.opsForValue().get("category:one");

        if (!StringUtils.isEmpty(categoryListJSON)) {
            log.info("从Redis缓存中查询到了所有的一级分类数据");
            return JSONUtil.toList(categoryListJSON, Category.class);
        }

        List<Category> categoryList = this.list(new LambdaQueryWrapper<Category>()
                .eq(Category::getStatus, 1)
                .eq(Category::getParentId, 0));

        redisTemplate.opsForValue().set("category:one", JSONUtil.toJsonStr(categoryList), 7, TimeUnit.DAYS);

        return categoryList;
    }

    @Override
    /**
     * 查询分类树
     * 该方法用于递归查询并构建分类树结构，主要步骤如下：
     * 1. 从数据库中查询所有状态为1（有效）的分类信息
     * 2. 从查询结果中筛选出一级目录（父ID为0）
     * 3. 遍历一级目录，查找其对应的二级目录，并设置为子目录
     * 4. 遍历二级目录，查找其对应的三级目录，并设置为子目录
     *
     * @return 返回构建好的分类树列表
     */
    @Cacheable(value = "category", key = "'all'")
    public List<Category> findCategoryTree() {
        // 查询所有有效状态的分类信息
        List<Category> categoryList = this.list(
                new LambdaQueryWrapper<Category>().eq(Category::getStatus, 1));

        // 设置一级目录
        List<Category> oneList = categoryList.stream().filter(item -> (item.getParentId() == 0)).collect(Collectors.toList());

        // 如果一级目录不为空，则继续设置二级目录和三级目录
        if (!CollectionUtil.isEmpty(oneList)) {
            // 设置二级目录
            oneList.forEach(one -> {
                List<Category> twoList = categoryList
                        .stream()
                        .filter(item -> (item.getParentId().longValue() == one.getId().longValue()))
                        .collect(Collectors.toList());
                one.setChildren(twoList);

                if (!CollectionUtil.isEmpty(twoList)) {
                    // 设置三级目录
                    twoList.forEach(two -> {
                        List<Category> three = categoryList
                                .stream()
                                .filter(item -> (item.getParentId().longValue() == two.getId().longValue()))
                                .collect(Collectors.toList());
                        two.setChildren(three);
                    });
                }
            });
        }
        return oneList;
    }
}
