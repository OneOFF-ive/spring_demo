package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.common.BaseContext;
import com.five.spring_demo.common.CustomException;
import com.five.spring_demo.entity.*;
import com.five.spring_demo.mapper.OrderMapper;
import com.five.spring_demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @Autowired
    AddressBookService addressBookService;

    @Autowired
    OrderDetailService orderDetailService;

    @Override
    public void submit(Order order) {
        // 获取用户ID
        Long userId = BaseContext.getCurrentId();

        // 获取购物车信息
        LambdaQueryWrapper<ShoppingCart> shoppingCartQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartQueryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartQueryWrapper);
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车为空，不能下单");
        }

        // 查询用户信息
        User user = userService.getById(userId);

        // 查询地址信息
        Long addressBookId = order.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("用户信息有误，不能下单");
        }

        long orderId = IdWorker.getId();    // 订单号
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        order.setId(orderId);
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(LocalDateTime.now());
        order.setStatus(2);
        order.setAmount(new BigDecimal(amount.get()));//总金额
        order.setUserId(userId);
        order.setNumber(String.valueOf(orderId));
        order.setUserName(user.getName());
        order.setConsignee(addressBook.getConsignee());
        order.setPhone(addressBook.getPhone());
        order.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(order);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(shoppingCartQueryWrapper);
    }
}
