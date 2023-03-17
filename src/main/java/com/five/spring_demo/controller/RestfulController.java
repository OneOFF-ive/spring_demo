package com.five.spring_demo.controller;

import com.five.spring_demo.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestfulController {
    @GetMapping("/user/{id}")
    String getUser(@PathVariable String id) {
        return id;
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
