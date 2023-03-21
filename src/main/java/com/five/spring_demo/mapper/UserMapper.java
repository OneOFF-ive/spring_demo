package com.five.spring_demo.mapper;

import com.five.spring_demo.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper{
    List<User> selectAll();
}
