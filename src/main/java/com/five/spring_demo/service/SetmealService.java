package com.five.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.five.spring_demo.dto.SetmealDto;
import com.five.spring_demo.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
}
