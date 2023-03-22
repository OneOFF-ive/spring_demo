package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.five.spring_demo.common.R;
import com.five.spring_demo.entity.Employee;
import com.five.spring_demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if (emp == null || !Objects.equals(emp.getPassword(), password)) {
            return R.error("登陆失败");
        }
        else if (emp.getStatus() == 0) {
            return R.error("用户已禁用");
        }
        request.getSession().setAttribute("employeeId", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeId");
        return R.success("退出成功");
    }

}
