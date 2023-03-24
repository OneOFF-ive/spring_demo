package com.five.spring_demo;

import com.five.spring_demo.config.MybatisPlusConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@SpringBootApplication
@MapperScan("com.five.spring_demo.mapper")
//@ServletComponentScan
public class SpringDemoApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MybatisPlusConfig.class);
        SpringApplication.run(SpringDemoApplication.class, args);
        log.info("项目启动成功");
    }

}
