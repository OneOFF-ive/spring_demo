package com.five.spring_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.five.spring_demo.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    Boolean isAdmin(HttpServletRequest request);
}
