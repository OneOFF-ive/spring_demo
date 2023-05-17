package com.five.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.five.spring_demo.entity.Order;

public interface OrderService extends IService<Order> {
    void submit(Order order);

    void again(Order order);
}
