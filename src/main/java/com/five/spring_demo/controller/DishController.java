package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.common.R;
import com.five.spring_demo.dto.DishDto;
import com.five.spring_demo.entity.Category;
import com.five.spring_demo.entity.Dish;
import com.five.spring_demo.entity.DishFlavor;
import com.five.spring_demo.service.CategoryService;
import com.five.spring_demo.service.DishFlavorService;
import com.five.spring_demo.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page<DishDto>> getPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((dish) -> {
            Long categoryId = dish.getCategoryId();
            Category category = categoryService.getById(categoryId);

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @GetMapping("/{id}")
    R<DishDto> getDish(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    R<String> updateDish(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("菜品信息修改成功");
    }

    @PostMapping("/status/{status}")
    R<String> updateStatus(@RequestParam("ids") List<Long> ids, @PathVariable String status) {
        UpdateWrapper<Dish> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids)
                        .set("status", status);
        dishService.update(updateWrapper);
        return R.success("修改菜品状态成功");
    }

    @DeleteMapping
    R<String> delete(@RequestParam("ids") List<Long> ids) {
        dishService.deleteWithFlavor(ids);
        return R.success("菜品删除成功");
    }

    @GetMapping("/list")
    R<List<Dish>> getDishByCategoryId(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList = dishService.list(queryWrapper);
        return R.success(dishList);
    }
}
