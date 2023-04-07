package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.entity.User;
import com.five.spring_demo.mapper.UserMapper;
import com.five.spring_demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
