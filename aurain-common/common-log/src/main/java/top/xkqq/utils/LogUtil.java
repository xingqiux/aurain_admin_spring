package top.xkqq.utils;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.xkqq.annotation.Log;
import top.xkqq.entity.system.SysOperLog;
import top.xkqq.util.AuthContextUtil;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 操作日志工具类
 * 用于在AOP切面中收集和处理操作日志信息
 */
public class LogUtil {

    /**
     * 在目标方法执行前调用，收集请求相关信息
     *
     * @param sysLog     自定义的日志注解，包含日志配置信息
     * @param sysOperLog 日志实体对象，用于存储日志信息
     * @param joinPoint  AOP连接点，包含目标方法的信息
     */
    public static void before(Log sysLog, SysOperLog sysOperLog,
                              ProceedingJoinPoint joinPoint) {
        // 1. 设置基本操作信息（从注解中获取）
        sysOperLog.setTitle(sysLog.title());                     // 设置模块标题
        sysOperLog.setOperatorType(sysLog.operatorType().name()); // 设置操作类型（后台用户/移动端用户等）
        sysOperLog.setBusinessType(sysLog.businessType());        // 设置业务类型（新增/修改/删除等）

        // 2. 获取目标方法的详细信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 设置方法的完整路径（类名+方法名）
        sysOperLog.setMethod(method.getDeclaringClass().getName() + "." + method.getName());

        // 3. 获取HTTP请求相关参数
        // 从当前线程获取请求上下文
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 设置请求相关信息
        sysOperLog.setRequestMethod(request.getMethod());     // 请求方式(GET/POST等)
        sysOperLog.setOperUrl(request.getRequestURI());       // 请求URI
        sysOperLog.setOperIp(request.getRemoteAddr());        // 客户端IP地址

        // 4. 根据配置决定是否保存请求参数
        if (sysLog.isSaveRequestData()) {
            // 只保存PUT和POST请求的参数
            String requestMethod = sysOperLog.getRequestMethod();
            if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                // 将请求参数转换为字符串
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }

        // 5. 设置操作人信息（从当前登录用户上下文获取）
        sysOperLog.setOperName(AuthContextUtil.get().getUsername());
    }

    /**
     * 在目标方法执行后调用，收集响应结果和执行状态
     *
     * @param sysLog     自定义的日志注解，包含日志配置信息
     * @param proceed    目标方法的执行结果
     * @param sysOperLog 日志实体对象，用于存储日志信息
     * @param status     操作状态(0成功,1失败)
     * @param errorMsg   错误信息(失败时记录)
     */
    public static void after(Log sysLog, Object proceed,
                             SysOperLog sysOperLog, int status,
                             String errorMsg) {
        // 1. 根据配置决定是否保存响应结果
        if (sysLog.isSaveResponseData()) {
            // 将响应结果转换为JSON字符串
            sysOperLog.setJsonResult(JSON.toJSONString(proceed));
        }

        // 2. 设置操作状态和错误信息
        sysOperLog.setStatus(status);     // 0表示正常，1表示异常
        sysOperLog.setErrorMsg(errorMsg); // 记录错误信息(如果有)
    }
}
