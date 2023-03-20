package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.entity.Category;
import com.five.spring_demo.mapper.CategoryMapper;
import com.five.spring_demo.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
