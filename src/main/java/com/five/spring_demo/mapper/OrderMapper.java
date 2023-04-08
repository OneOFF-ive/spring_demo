package com.five.spring_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.five.spring_demo.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
