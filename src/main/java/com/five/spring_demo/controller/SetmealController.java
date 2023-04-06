package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.dto.DishDto;
import com.five.spring_demo.dto.SetmealDto;
import com.five.spring_demo.entity.Category;
import com.five.spring_demo.entity.Dish;
import com.five.spring_demo.entity.Setmeal;
import com.five.spring_demo.service.CategoryService;
import com.five.spring_demo.service.SetMealDishService;
import com.five.spring_demo.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.five.spring_demo.common.R;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @Autowired
    SetMealDishService setMealDishService;

    @Autowired
    CategoryService categoryService;


    @PostMapping
     R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("保存套餐成功");
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> getPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((setmeal) -> {
            Long categoryId = setmeal.getCategoryId();
            Category category = categoryService.getById(categoryId);

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }
}
