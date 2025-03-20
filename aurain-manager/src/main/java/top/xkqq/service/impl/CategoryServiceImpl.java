package top.xkqq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.xkqq.entity.product.Category;
import top.xkqq.listener.ExcelListener;
import top.xkqq.mapper.CategoryMapper;
import top.xkqq.service.CategoryService;
import top.xkqq.vo.product.CategoryExcelVo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 缓存相关常量
    private static final String CACHE_PREFIX = "category:parent:";
    private static final Duration BASE_CACHE_TIME = Duration.ofMinutes(30);
    private static final Duration RANDOM_CACHE_TIME_RANGE = Duration.ofMinutes(10);
    // 改用StringRedisTemplate配合JSON序列化
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据父分类ID查找分类列表
     * 首先尝试从Redis缓存中获取数据，如果缓存未命中，则查询数据库，并将结果写入缓存
     *
     * @param parentId 父分类ID
     * @return 分类列表
     */
    @Override
    public List<Category> findByParentId(Long parentId) {
        String cacheKey = CACHE_PREFIX + parentId;

        try {
            //1.尝试从缓存获取
            String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
            if (StringUtils.hasText(cachedData)) {
                return deserializeCategories(cachedData);
            }
        } catch (Exception e) {
            log.error("Redis查询失败，parentId: {}，异常: {}" + parentId + e.getMessage(), e);
        }

        // 2. 缓存未命中，查询数据库
        List<Category> categories = queryFromDatabase(parentId);

        try {
            // 3. 异步缓存写入（不影响主进程）
            CompletableFuture.runAsync(() -> {
                String jsonData = serializeCategories(categories);
                Duration expiration = BASE_CACHE_TIME.plus(
                        Duration.ofSeconds(new Random().nextInt((int) RANDOM_CACHE_TIME_RANGE.getSeconds()))
                );
                stringRedisTemplate.opsForValue().set(
                        cacheKey,
                        jsonData,
                        expiration);
            }).exceptionally(e -> {
                log.error("Redis缓存写入失败，parentId: {}，异常: {}" + parentId + e.getMessage(), e);
                return null;
            });
        } catch (Exception e) {
            log.error("缓存写入异常，parentId: {}" + parentId + e);
        }

        return categories;
    }

    /**
     * 从数据库中查询分类列表
     *
     * @param parentId 父分类ID
     * @return 分类列表
     */
    private List<Category> queryFromDatabase(Long parentId) {
        // 查询所有的分类
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, parentId);
        List<Category> categoryList = categoryMapper.selectList(wrapper);

        if (!CollectionUtil.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                // 查询该分类下子分类的数量
                LambdaQueryWrapper<Category> countWrapper = new LambdaQueryWrapper<>();
                countWrapper.eq(Category::getParentId, category.getId());
                Long count = categoryMapper.selectCount(countWrapper);

                // 如果大于 0 说明有子分类
                category.setHasChildren(count > 0);
            });
        }
        return categoryList;
    }

    /**
     * JSON 序列化方法
     * 将分类列表转换为JSON字符串
     *
     * @param categories 分类列表
     * @return JSON字符串
     */
    private String serializeCategories(List<Category> categories) {
        return JSON.toJSONString(categories,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * JSON 反序列化方法
     * 将JSON字符串转换为分类列表
     *
     * @param json JSON字符串
     * @return 分类列表
     */
    private List<Category> deserializeCategories(String json) {
        return JSON.parseArray(json, Category.class);
}


    /**
     * 导出数据到Excel
     *
     * @param response HTTP响应对象，用于设置响应头和输出流
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", StandardCharsets.UTF_8);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 查询数据库中的数据,并使用数据库的数据量初始化excelVo 的大小
            List<Category> categoryList = categoryMapper.selectList(null);
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            // 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
            categoryList.forEach(category -> {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtil.copyProperties(category, categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            });

            // 写出数据到浏览器端
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据")
                    .doWrite(categoryExcelVoList);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入数据方法
     * 该方法负责读取上传的Excel文件内容，并将其解析到系统中
     * 主要用途是批量导入类别数据
     *
     * @param file 用户上传的包含类别数据的Excel文件
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(MultipartFile file) {
        //调用read方法读取excel数据
        try {
            EasyExcel.read(file.getInputStream(),
                            CategoryExcelVo.class,
                            new ExcelListener(this))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            // 如果在读取文件流时发生IOException，将其转换为RuntimeException并抛出
            throw new RuntimeException(e);
        }
    }
}
