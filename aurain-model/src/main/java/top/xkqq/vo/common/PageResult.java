package top.xkqq.vo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> {

    // 总记录数
    private long total;

    // 数据列表
    private List<T> list;

    // 当前页码 (对应Page.current)
    private long pageNum;

    // 每页记录数
    private long pageSize;

    // 当前页记录数
    private long size;

    // 起始行号
    private long startRow;

    // 结束行号
    private long endRow;

    // 总页数
    private long pages;

    // 上一页
    private long prePage;

    // 下一页
    private long nextPage;

    // 是否为第一页
    private boolean isFirstPage;

    // 是否为最后一页
    private boolean isLastPage;

    // 是否有上一页
    private boolean hasPreviousPage;

    // 是否有下一页
    private boolean hasNextPage;

    // 导航页码数
    private int navigatePages = 10; // 默认10

    // 所有导航页号
    private List<Long> navigatepageNums;

    // 导航条上的第一页
    private long navigateFirstPage;

    // 导航条上的最后一页
    private long navigateLastPage;

    // 从MyBatis-Plus的Page对象转换
    public static <T> PageResult<T> fromPage(Page<T> page) {
        PageResult<T> result = new PageResult<>();

        // 设置基本分页信息
        result.setTotal(page.getTotal());
        result.setList(page.getRecords());
        result.setPageNum(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setSize(page.getRecords().size());

        // 计算总页数
        long pages = page.getTotal() / page.getSize();
        if (page.getTotal() % page.getSize() != 0) {
            pages++;
        }
        result.setPages(pages);

        // 计算起始行号和结束行号
        result.setStartRow((page.getCurrent() - 1) * page.getSize() + 1);
        result.setEndRow(Math.min(page.getCurrent() * page.getSize(), page.getTotal()));

        // 计算上一页和下一页
        result.setPrePage(page.getCurrent() > 1 ? page.getCurrent() - 1 : 0);
        result.setNextPage(page.getCurrent() < pages ? page.getCurrent() + 1 : 0);

        // 设置页面状态
        result.setFirstPage(page.getCurrent() == 1);
        result.setLastPage(page.getCurrent() == pages || pages == 0);
        result.setHasPreviousPage(page.getCurrent() > 1);
        result.setHasNextPage(page.getCurrent() < pages);

        // 计算导航页码
        calcNavigatepageNums(result);

        return result;
    }

    // 计算导航页码
    private static <T> void calcNavigatepageNums(PageResult<T> result) {
        // 当总页数小于等于导航页码数时，导航页为所有页码
        if (result.getPages() <= result.getNavigatePages()) {
            result.setNavigatepageNums(rangeLongList(1, result.getPages()));
        } else {
            // 当总页数大于导航页码数时，计算起始和结束导航页
            long startNum = result.getPageNum() - result.getNavigatePages() / 2;
            long endNum = result.getPageNum() + result.getNavigatePages() / 2;

            // 修正起始和结束导航页
            if (startNum < 1) {
                startNum = 1;
                endNum = result.getNavigatePages();
            }

            if (endNum > result.getPages()) {
                endNum = result.getPages();
                startNum = result.getPages() - result.getNavigatePages() + 1;
                if (startNum < 1) {
                    startNum = 1;
                }
            }

            result.setNavigatepageNums(rangeLongList(startNum, endNum));
        }

        // 设置导航条上的第一页和最后一页
        if (!result.getNavigatepageNums().isEmpty()) {
            result.setNavigateFirstPage(result.getNavigatepageNums().get(0));
            result.setNavigateLastPage(result.getNavigatepageNums().get(result.getNavigatepageNums().size() - 1));
        } else {
            result.setNavigateFirstPage(1);
            result.setNavigateLastPage(1);
        }
    }

    // 生成指定范围的Long列表
    private static List<Long> rangeLongList(long start, long end) {
        if (start > end) {
            return Collections.emptyList();
        }

        List<Long> result = new java.util.ArrayList<>();
        for (long i = start; i <= end; i++) {
            result.add(i);
        }
        return result;
    }
}
