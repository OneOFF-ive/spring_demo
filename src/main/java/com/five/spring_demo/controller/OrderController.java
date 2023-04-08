package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.common.R;
import com.five.spring_demo.dto.OrderDto;
import com.five.spring_demo.entity.Order;
import com.five.spring_demo.entity.OrderDetail;
import com.five.spring_demo.service.OrderDetailService;
import com.five.spring_demo.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/userPage")
    public R<Page<OrderDto>> userPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.orderByDesc(Order::getOrderTime);
        orderService.page(orderPage, orderQueryWrapper);

        Page<OrderDto> orderDtoPage = new Page<>();
        BeanUtils.copyProperties(orderPage, orderDtoPage, "records");
        List<Order> records = orderPage.getRecords();

        List<OrderDto> orderDtoList = records.stream().map(order -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);

            String orderNum = order.getNumber();
            LambdaQueryWrapper<OrderDetail> orderDetailQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailQueryWrapper.eq(OrderDetail::getNumber, orderNum);
            List<OrderDetail> orderDetails = orderDetailService.list(orderDetailQueryWrapper);
            orderDto.setOrderDetails(orderDetails);
            return orderDto;
        }).collect(Collectors.toList());
        orderDtoPage.setRecords(orderDtoList);
        return R.success(orderDtoPage);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Order order) {
        orderService.submit(order);
        return R.success("下单成功");
    }
}
