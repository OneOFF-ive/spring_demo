package com.five.spring_demo.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]");
        try {
            metaObject.setValue("createTime", LocalDateTime.now());
            metaObject.setValue("updateTime", LocalDateTime.now());
            metaObject.setValue("createUser", BaseContext.getCurrentId());
            metaObject.setValue("updateUser", BaseContext.getCurrentId());
        }
        catch (ReflectionException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]");
        try {
            metaObject.setValue("updateTime", LocalDateTime.now());
            metaObject.setValue("updateUser", BaseContext.getCurrentId());
        }
        catch (ReflectionException e) {
            log.error(e.getMessage());
        }
    }
}
