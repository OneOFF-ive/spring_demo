package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.common.CustomException;
import com.five.spring_demo.entity.Category;
import com.five.spring_demo.entity.Dish;

import com.five.spring_demo.entity.Employee;
import com.five.spring_demo.entity.Setmeal;
import com.five.spring_demo.mapper.CategoryMapper;
import com.five.spring_demo.service.CategoryService;
import com.five.spring_demo.service.DishService;
import com.five.spring_demo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    SetmealService setmealService;

    @Autowired
    DishService dishService;

    @Override
    public boolean remove(Long id) {
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        if (dishService.count(dishQueryWrapper) > 0) {
            throw new CustomException("当前分类关联菜品信息，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);
        if (setmealService.count(setmealQueryWrapper) > 0) {
            throw new CustomException("当前分类关联套餐信息，不能删除");
        }
        return super.removeById(id);
    }
}
