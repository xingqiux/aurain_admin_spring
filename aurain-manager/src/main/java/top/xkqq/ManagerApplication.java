package top.xkqq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.xkqq.properties.UserAuthProperties;


@EnableConfigurationProperties(value = {UserAuthProperties.class})
@SpringBootApplication
@MapperScan("top.xkqq.mapper")
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }


}
