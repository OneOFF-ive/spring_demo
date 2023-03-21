package com.five.spring_demo.controller;

import com.five.spring_demo.entity.User;
import com.five.spring_demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestfulController {

    private final UserMapper userMapper;

    public RestfulController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/user/{id}")
    String getUser(@PathVariable String id) {
        return id;
    }

    @GetMapping("/user")
    List<User> getAllUser() {
       return userMapper.selectAll();
    }

    @PostMapping("/user")
    void createUser(User user) {
        System.out.println(user);
    }

    @PutMapping("/user")
    void update(User user) {
        System.out.println(user);
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable String id) {
        return id;
    }
}
