package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.common.BaseContext;
import com.five.spring_demo.common.R;
import com.five.spring_demo.dto.OrderDto;
import com.five.spring_demo.entity.Order;
import com.five.spring_demo.entity.OrderDetail;
import com.five.spring_demo.service.OrderDetailService;
import com.five.spring_demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/userPage")
    public R<Page<OrderDto>> userPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        Page<Order> orderPage = new Page<>(page, pageSize);

        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<Order> orderQueryWrapper = new LambdaQueryWrapper<>();
        orderQueryWrapper.eq(userId != null, Order::getUserId, userId);
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

    @GetMapping("/page")
    public R<Page<Order>> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String number, String beginTime, String endTime) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime begin = null;
        LocalDateTime end = null;
        if (beginTime != null) {
            begin = LocalDateTime.parse(beginTime, formatter);
        }
        if (endTime != null) {
            end = LocalDateTime.parse(endTime, formatter);
        }

        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null, Order::getNumber, number);
        queryWrapper.orderByAsc(Order::getOrderTime);
        queryWrapper.gt(begin != null, Order::getOrderTime, begin);
        queryWrapper.lt(end != null, Order::getOrderTime, end);

        orderService.page(orderPage, queryWrapper);

        return R.success(orderPage);
    }

    @PutMapping
    public R<String> setStatus(@RequestBody Order order) {
        orderService.updateById(order);
        return R.success("修改送达状态成功");
    }
}
