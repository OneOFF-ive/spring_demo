package com.five.spring_demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.five.spring_demo.common.BaseContext;
import com.five.spring_demo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        log.info("拦截到请求:{}", request.getRequestURI());

        if (request.getSession().getAttribute("employee") != null) {
            log.info("拦截通过:{}", request.getRequestURI());
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            return true;
        }
        else if (request.getSession().getAttribute("user") != null) {
            log.info("拦截通过:{}", request.getRequestURI());
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            return true;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截未通过:{}", request.getRequestURI());
        return false;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, @Nullable Exception ex) {
        log.info("请求结束:{}", request.getRequestURI());
        BaseContext.removeCurrentId();
    }
}
