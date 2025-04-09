package top.xkqq.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.xkqq.entity.order.OrderStatistics;
import top.xkqq.product.mapper.OrderInfoMapper;
import top.xkqq.product.mapper.OrderStatisticsMapper;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    // 每天凌晨2点执行订单总额统计任务
    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        // 获取前一天的日期，格式为yyyy-MM-dd
        String createTime = DateUtil.offsetDay(new Date(), -1).toString(new SimpleDateFormat("yyyy-MM-dd"));
        // 根据日期查询订单统计信息
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        // 如果查询到的订单统计信息不为空，则插入到数据库中
        if (orderStatistics != null) {
            orderStatisticsMapper.insert(orderStatistics);
        }
    }

}
