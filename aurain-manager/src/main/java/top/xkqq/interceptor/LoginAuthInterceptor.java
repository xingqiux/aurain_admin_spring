package top.xkqq.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1 获取请求方法，如果是 options 直接放行

        // 2 获取请求头中的 token

        // 3 如果 token 为空返回错误提示，如果不为空查询 redis

        // 4 如果查询到用户信息，则存储到 ThreadLocal 中，否则返回错误提示

        // 5 更新 redis 用户信息的过期时间

        // 6 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 释放 ThreadLocal 中的数据
    }

}
