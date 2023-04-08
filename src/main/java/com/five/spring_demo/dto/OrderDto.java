package com.five.spring_demo.dto;

import com.five.spring_demo.entity.Order;
import com.five.spring_demo.entity.OrderDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDto extends Order {
    private List<OrderDetail> orderDetails;
}
