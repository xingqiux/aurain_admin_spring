package top.xkqq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.order.OrderStatistics;

@Mapper
public interface OrderInfoMapper {

    // 查询指定日期产生的订单数据
    OrderStatistics selectOrderStatistics(String creatTime);
}
