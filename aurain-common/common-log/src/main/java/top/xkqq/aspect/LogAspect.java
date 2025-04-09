package top.xkqq.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.xkqq.annotation.Log;
import top.xkqq.entity.system.SysOperLog;
import top.xkqq.event.SysLogEvent;
import top.xkqq.utils.LogUtil;

/**
 * 操作日志切面类
 * 用于拦截带有@Log注解的方法，记录操作日志
 */
@Aspect           // 声明这是一个切面类
@Component        // 注册为Spring组件
@Slf4j            // 启用日志
@Order(1)         // 设置切面优先级(数值越小优先级越高)
public class LogAspect {

    @Autowired
    private ApplicationEventPublisher eventPublisher;  // 注入Spring事件发布器

    /**
     * 环绕通知，拦截带有@Log注解的方法
     *
     * @param join 连接点，包含目标方法的上下文信息
     * @param log  被拦截方法上的Log注解实例
     * @return 目标方法的返回值
     */
    @Around(value = "@annotation(log)")
    // @Around通知要求ProceedingJoinPoint作为第一个参数，因为它需要控制目标方法的执行
    public Object doAroundAdvice(ProceedingJoinPoint join, Log log) {

        // 1. 创建日志对象并收集请求前的信息
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.before(log, sysOperLog, join);

        Object proceed = null;

        try {
            // 2. 执行目标业务方法
            proceed = join.proceed();

            // 3. 方法执行成功，收集响应信息(状态为0表示成功)
            LogUtil.after(log, proceed, sysOperLog, 0, null);
        } catch (Throwable e) {
            // 4. 方法执行异常，收集异常信息(状态为1表示失败)
            LogUtil.after(log, proceed, sysOperLog, 1, e.getMessage());
            // 重新抛出异常，确保事务能够正常回滚
            throw new RuntimeException(e);
        }

        // 5. 发布日志事件，解耦日志记录和存储逻辑
        eventPublisher.publishEvent(new SysLogEvent(this, sysOperLog));

        // 6. 返回原始方法的执行结果
        return proceed;
    }
}
