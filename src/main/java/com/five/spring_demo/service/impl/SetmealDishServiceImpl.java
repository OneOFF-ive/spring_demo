package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.entity.Dish;
import com.five.spring_demo.entity.SetmealDish;
import com.five.spring_demo.mapper.SetmealDishMapper;
import com.five.spring_demo.service.DishService;
import com.five.spring_demo.service.SetMealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetMealDishService {
    @Autowired
    DishService dishService;

    @Override
    public List<Dish> getDishById(Long id) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.inSql(Dish::getId, "SELECT `dish_id` FROM setmeal_dish WHERE `setmeal_id` = " + id);
        return dishService.list(wrapper);
    }
}
