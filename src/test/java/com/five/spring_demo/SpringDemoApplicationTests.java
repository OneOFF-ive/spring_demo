package com.five.spring_demo;

import com.five.spring_demo.common.SMSUtils;
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
        SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", "18939162492", "1234");
    }

}
