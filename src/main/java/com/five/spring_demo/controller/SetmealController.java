package com.five.spring_demo.controller;

import com.five.spring_demo.dto.SetmealDto;
import com.five.spring_demo.service.SetMealDishService;
import com.five.spring_demo.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.five.spring_demo.common.R;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @Autowired
    SetMealDishService setMealDishService;


    @PostMapping
     R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("保存套餐成功");
    }
}
