package com.five.spring_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringDemoApplicationTests {

    @Test
    void contextLoads() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5);
        l.stream().map(n -> n = n*2);
        System.out.println(l);
    }

}
