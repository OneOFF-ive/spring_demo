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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = " + code);
            SMSUtils.sendMessage("阿里云短信测试", "SMS_154950909", phone, code);
            session.setAttribute(phone, code);
            R.success("手机验证码短信发送成功");
        }
        return R.error("手机验证码短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, ?> map, HttpSession session) {
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");

        String codeInSession = (String) session.getAttribute(phone);

        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登陆失败");
    }
}
