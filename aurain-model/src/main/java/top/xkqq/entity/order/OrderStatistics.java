package top.xkqq.entity.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatistics {
    private Date orderDate;
    private BigDecimal totalAmount;
    private Integer totalNum;
}
