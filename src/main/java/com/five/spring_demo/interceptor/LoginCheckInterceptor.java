package com.five.spring_demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.five.spring_demo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截到请求:{}", request.getRequestURI());

        if (request.getSession().getAttribute("employee") == null) {
            log.info("未登录{}", request.getRequestURI());
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return false;
        }

        log.info("已登录");
        return true;
    }
}
