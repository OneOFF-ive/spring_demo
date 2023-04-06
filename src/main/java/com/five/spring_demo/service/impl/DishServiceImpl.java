package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.dto.DishDto;
import com.five.spring_demo.entity.Dish;
import com.five.spring_demo.entity.DishFlavor;
import com.five.spring_demo.mapper.DishMapper;
import com.five.spring_demo.service.DishFlavorService;
import com.five.spring_demo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((flavor) -> {
            flavor.setDishId(dishId);
            return flavor;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(dishDto.getFlavors());
    }
}
