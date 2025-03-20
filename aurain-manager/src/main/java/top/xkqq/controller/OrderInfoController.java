package top.xkqq.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xkqq.dto.OrderStatisticsDto;
import top.xkqq.service.OrderInfoService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.order.OrderStatisticsVo;

@RestController
@RequestMapping(value = "/admin/order/orderInfo")
@Slf4j
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 获取订单统计信息
     * 本方法通过接收订单统计DTO参数，调用服务层方法获取订单统计信息，并返回给客户端
     * 主要用于提供订单相关的统计数据分析，如订单数量、金额等
     *
     * @param orderStatisticsDto 订单统计DTO，包含统计所需的查询条件
     * @return 返回一个Result对象，其中封装了订单统计信息VO和成功结果码
     */
    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        // 调用服务层方法获取订单统计信息
        log.info("接收到的订单统计DTO参数：{}", orderStatisticsDto);
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto);
        log.info("订单统计信息：{}", orderStatisticsVo);
        // 构建并返回成功结果，封装订单统计信息VO
        return Result.build(orderStatisticsVo, ResultCodeEnum.SUCCESS);
    }

}
