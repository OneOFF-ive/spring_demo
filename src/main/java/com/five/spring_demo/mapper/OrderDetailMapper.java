package com.five.spring_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.five.spring_demo.entity.OrderDetail;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
