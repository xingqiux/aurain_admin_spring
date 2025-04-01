package xkqq.top.annotation;

import org.springframework.context.annotation.Import;
import xkqq.top.aspect.LogAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = LogAspect.class)        // 通过Import注解导入日志切面类到Spring容器中
public @interface EnableLogAspect {
}
