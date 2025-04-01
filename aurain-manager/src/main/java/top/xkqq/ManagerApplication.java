package top.xkqq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.xkqq.properties.UserAuthProperties;
import xkqq.top.annotation.EnableLogAspect;

// 启动日志和异步执行
@EnableLogAspect
@EnableAsync
@EnableConfigurationProperties(value = {UserAuthProperties.class})
@SpringBootApplication
@EnableScheduling
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }


}
