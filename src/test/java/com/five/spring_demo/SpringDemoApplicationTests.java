package com.five.spring_demo;

import com.five.spring_demo.entity.User;
import com.five.spring_demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringDemoApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        Integer a =3;
        Integer b =4;
        swap(a, b);
        System.out.println(a + "\t" +b);
    }

    void swap(Integer a, Integer b) {
        Integer c = a;
        a = b;
        b = c;
    }

    @Test
    void testSelect() {
        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
//        userList.forEach(System.out::println);
    }

}
