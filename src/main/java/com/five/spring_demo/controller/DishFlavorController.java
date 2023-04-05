package com.five.spring_demo.controller;

import com.five.spring_demo.entity.DishFlavor;
import com.five.spring_demo.service.DishFlavorService;
import com.five.spring_demo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishFlavorController {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;
}
