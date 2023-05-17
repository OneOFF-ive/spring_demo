package com.five.spring_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.five.spring_demo.common.R;
import com.five.spring_demo.common.SMSUtils;
import com.five.spring_demo.common.ValidateCodeUtils;
import com.five.spring_demo.entity.User;
import com.five.spring_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = " + code);
            SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", phone, code);

            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            R.success("手机验证码短信发送成功");
        }
        return R.error("手机验证码短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, ?> map, HttpSession session) {
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");

        String codeInRedis = (String) redisTemplate.opsForValue().get(phone);

        if (codeInRedis != null && codeInRedis.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setName(phone);
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登陆失败");
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session) {
        session.removeAttribute("user");
        return R.success("退出成功");
    }

    @GetMapping("/{phone}")
    public R<User> getUserByPhone(@PathVariable String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(phone!=null, User::getPhone, phone);
        User user = userService.getOne(queryWrapper);
        return R.success(user);
    }

}
