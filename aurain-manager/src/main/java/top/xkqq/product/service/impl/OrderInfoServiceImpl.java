package top.xkqq.product.service.impl;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.dto.OrderStatisticsDto;
import top.xkqq.entity.order.OrderStatistics;
import top.xkqq.product.mapper.OrderStatisticsMapper;
import top.xkqq.product.service.OrderInfoService;
import top.xkqq.vo.order.OrderStatisticsVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;


    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {

        // 查询统计结果数据集
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        log.info("查询统计结果数据集：{}", orderStatisticsList);

        // 日期列表
        List<String> dateList = orderStatisticsList.stream()
                .map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList());

        // 统计金额列表
        List<BigDecimal> amountList = orderStatisticsList.stream()
                .map(OrderStatistics::getTotalAmount)
                .collect(Collectors.toList());

        // 创建OrderStatisticsVo对象封装响应结果数据
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        return orderStatisticsVo;
    }
}
