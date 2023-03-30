package com.five.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.five.spring_demo.entity.Category;

public interface CategoryService extends IService<Category> {
    boolean remove(Long id);
}
