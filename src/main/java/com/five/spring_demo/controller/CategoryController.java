package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.common.R;
import com.five.spring_demo.entity.Category;
import com.five.spring_demo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        if (categoryService.save(category)) {

            return R.success("新增分类成功");
        }
        return R.error("新增分类失败");
    }

    @GetMapping("/page")
    R<Page<Category>> getPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    R<String> delete(@RequestParam("ids") Long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @PutMapping
    R<String> update(@RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.update(category);
        return R.success("更新成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }

}
