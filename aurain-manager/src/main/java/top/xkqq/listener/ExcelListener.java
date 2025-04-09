package top.xkqq.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import top.xkqq.entity.product.Category;
import top.xkqq.product.service.CategoryService;
import top.xkqq.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 建议注入Service而不是Mapper，以便使用事务管理
 * 添加适当的日志记录
 * 添加异常处理
 * 考虑数据验证逻辑
 * 可以添加进度监控
 * 建议使用统一的数据转换方法
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<CategoryExcelVo> {


    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private ArrayList<CategoryExcelVo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 获取 mapper 对象
//    @Autowired
    private final CategoryService categoryService;

    // 设置构造器
    public ExcelListener(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void invoke(CategoryExcelVo categoryExcelVo, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(categoryExcelVo));
        cachedDataList.add(categoryExcelVo);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

        // 存储完成清理 list
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // excel解析完毕以后需要执行的代码
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        try {
            if (!cachedDataList.isEmpty()) {
                // 转换VO到实体
                List<Category> categoryList = cachedDataList.stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());

                // 批量保存
                categoryService.saveBatch(categoryList);
            }
            log.info("存储数据库成功！");
        } catch (Exception e) {
            log.error("存储数据库失败！", e);
            throw new RuntimeException("批量保存数据失败", e);
        }
    }

    private Category convertToEntity(CategoryExcelVo vo) {
        Category category = new Category();
        BeanUtils.copyProperties(vo, category);
        return category;
    }
}
