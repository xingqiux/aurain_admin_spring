package top.xkqq.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.xkqq.entity.product.Category;
import top.xkqq.listener.ExcelListener;
import top.xkqq.mapper.CategoryMapper;
import top.xkqq.service.CategoryService;
import top.xkqq.vo.product.CategoryExcelVo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据parentId获取下级节点
     * 遍历分类的集合，获取每一个分类数据
     * 查询该分类下子分类的数量
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Category> findByParentId(Long parentId) {
        // 查询所有的分类
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, parentId);
        List<Category> categoryList = categoryMapper.selectList(wrapper);

        if (!CollectionUtil.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
//                查询该分类下子分类的数量
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
