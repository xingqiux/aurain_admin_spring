package top.xkqq.service;

import org.springframework.stereotype.Service;
import top.xkqq.dto.OrderStatisticsDto;
import top.xkqq.vo.order.OrderStatisticsVo;

@Service
public interface OrderInfoService {


    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
