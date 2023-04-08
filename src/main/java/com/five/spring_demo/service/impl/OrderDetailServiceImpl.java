package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.entity.OrderDetail;
import com.five.spring_demo.mapper.OrderDetailMapper;
import com.five.spring_demo.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
