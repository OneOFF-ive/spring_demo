package com.five.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.five.spring_demo.entity.Dish;
import com.five.spring_demo.entity.SetmealDish;

import java.util.List;

public interface SetMealDishService extends IService<SetmealDish> {
    List<Dish> getDishById(Long id);
}
