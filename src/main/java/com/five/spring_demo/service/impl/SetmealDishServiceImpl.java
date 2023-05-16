package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.dto.DishDto;
import com.five.spring_demo.dto.SetmealDto;
import com.five.spring_demo.entity.Dish;
import com.five.spring_demo.entity.SetmealDish;
import com.five.spring_demo.mapper.SetmealDishMapper;
import com.five.spring_demo.service.DishService;
import com.five.spring_demo.service.SetMealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetMealDishService {
    @Autowired
    DishService dishService;

    @Override
    public List<DishDto> getDishById(Long id) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.inSql(Dish::getId, "SELECT `dish_id` FROM setmeal_dish WHERE `setmeal_id` = " + id);
        List<Dish> dishList = dishService.list(wrapper);
        List<DishDto> dishDtoList = dishList.stream().map((item) -> {
            Long setmealId = id;
            Long dishId = item.getId();
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealId);
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getDishId, dishId);
            SetmealDish setmealDish = this.getOne(setmealDishLambdaQueryWrapper);

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setCopies(setmealDish.getCopies());
            return dishDto;
        }).collect(Collectors.toList());
        return dishDtoList;
    }
}
