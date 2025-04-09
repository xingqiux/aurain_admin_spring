package top.xkqq.product.mapper;

import top.xkqq.dto.OrderStatisticsDto;
import top.xkqq.entity.order.OrderStatistics;

import java.util.List;

public interface OrderStatisticsMapper {
    void insert(OrderStatistics orderStatistics);

    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);


}
