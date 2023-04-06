package com.five.spring_demo.dto;

import com.five.spring_demo.entity.Setmeal;
import com.five.spring_demo.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
