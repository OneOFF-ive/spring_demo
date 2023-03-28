package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.five.spring_demo.common.R;
import com.five.spring_demo.entity.Category;
import com.five.spring_demo.entity.Employee;
import com.five.spring_demo.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    R<String> generateCategory(@RequestBody Category category) {
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

}
