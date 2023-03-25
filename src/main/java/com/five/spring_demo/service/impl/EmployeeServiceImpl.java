package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.common.R;
import com.five.spring_demo.entity.Employee;
import com.five.spring_demo.mapper.EmployeeMapper;
import com.five.spring_demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public Boolean isAdmin(HttpServletRequest request) {
        Long empId = (Long) request.getSession().getAttribute("employee");
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getId, empId);
        Employee emp = this.getOne(queryWrapper);
        return Objects.equals(emp.getUsername(), "admin");
    }
}
