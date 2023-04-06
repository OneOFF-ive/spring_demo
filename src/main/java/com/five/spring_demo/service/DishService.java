package com.five.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.five.spring_demo.dto.DishDto;
import com.five.spring_demo.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getByIdWithFlavor(Long id);
    void updateWithFlavor(DishDto dishDto);
    void deleteWithFlavor(List<Long> id);
}
