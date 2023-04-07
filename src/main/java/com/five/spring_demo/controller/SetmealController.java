package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.dto.DishDto;
import com.five.spring_demo.dto.SetmealDto;
import com.five.spring_demo.entity.*;
import com.five.spring_demo.service.CategoryService;
import com.five.spring_demo.service.DishService;
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

    @Autowired
    DishService dishService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
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

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        setmealService.removeWithDish(ids);
        return R.success("套餐删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable String status, @RequestParam("ids") List<Long> ids) {
        UpdateWrapper<Setmeal> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids)
                .set("status", status);
        setmealService.update(updateWrapper);
        return R.success("修改套餐状态成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getSetmeal(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithDish(setmealDto);
        return R.success("套餐修改成功");
    }

    @GetMapping("/list")
    public R<List<SetmealDto>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> setmealList =setmealService.list(queryWrapper);
        List<SetmealDto> setmealDtoList = setmealList.stream().map((item) -> {
            Long setmealId = item.getId();
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealId);
            List<SetmealDish> setmealDishes = setMealDishService.list(setmealDishLambdaQueryWrapper);

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setSetmealDishes(setmealDishes);
            return setmealDto;
        }).collect(Collectors.toList());;

        return R.success(setmealDtoList);
    }

    @GetMapping("/dish/{setmeal_id}")
    public R<List<SetmealDish>> getDishById(@PathVariable Long setmeal_id) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal_id);
        List<SetmealDish> list = setMealDishService.list(queryWrapper);
        return R.success(list);
    }
}
