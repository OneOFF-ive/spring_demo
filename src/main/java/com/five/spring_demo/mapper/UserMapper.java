package com.five.spring_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.five.spring_demo.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
