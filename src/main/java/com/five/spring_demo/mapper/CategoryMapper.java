package com.five.spring_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.five.spring_demo.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
