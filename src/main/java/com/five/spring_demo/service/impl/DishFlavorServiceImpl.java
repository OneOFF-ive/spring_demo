package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.entity.DishFlavor;
import com.five.spring_demo.mapper.DishFlavorMapper;
import com.five.spring_demo.service.DishFlavorService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
