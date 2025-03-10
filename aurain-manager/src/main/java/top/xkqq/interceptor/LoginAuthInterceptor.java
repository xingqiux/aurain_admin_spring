package top.xkqq.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.xkqq.entity.system.SysUser;
import top.xkqq.util.AuthContextUtil;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 1 获取请求方法，如果是 options 直接放行
//        if (request.getMethod().equals("OPTIONS")){
//            return true;
//        }
//
//        // 2 请求路径为测试放行
//        if (request.getRequestURI().equals("/test/**")){
//            return true;
//        }
//
//
//        // 2 获取请求头中的 token
//        String token = request.getHeader("token");
//
//        // 3 如果 token 为空返回错误提示，如果不为空查询 redis
//        if (StrUtil.isEmpty(token)){
//            responseNoLoginInfo(response);
//            return  false;
//        }
//        SysUser sysUserInfo = (SysUser)redisTemplate.opsForValue().get("user:login" + token);
//
//        // 4 如果查询到用户信息，则存储到 ThreadLocal 中，否则返回错误提示
//        if (sysUserInfo == null){
//            responseNoLoginInfo(response);
//            return  false;
//        }
//
//        AuthContextUtil.set(sysUserInfo);
//        // 5 更新 redis 用户信息的过期时间
//        redisTemplate.expire("user:login" + token, 30, TimeUnit.MINUTES);

        // 6 放行
        return true;
    }

    /**
     * 向响应中写入未登录的信息
     * 当用户未登录时，通过此方法向HTTP响应中写入标准的未登录信息
     *
     * @param response HTTP响应对象，用于向客户端发送未登录的响应信息
     */
    private void responseNoLoginInfo(HttpServletResponse response) {
        // 构建一个结果对象，其中包含未登录的相关信息
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        // 设置响应的字符编码，确保兼容性
        response.setCharacterEncoding("UTF-8");
        // 设置响应的内容类型，指定为HTML文本
        response.setContentType("text/html; charset=utf-8");
        try {
            // 获取响应的输出流，用于向客户端发送信息
            writer = response.getWriter();
            // 将结果对象转换为JSON字符串并输出到响应中
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            // 打印异常信息，以便于调试和日志记录
            e.printStackTrace();
        } finally {
            // 确保输出流被正确关闭，避免资源泄露
            if (writer != null) writer.close();
        }
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 释放 ThreadLocal 中的数据
        AuthContextUtil.remove();
    }

}
