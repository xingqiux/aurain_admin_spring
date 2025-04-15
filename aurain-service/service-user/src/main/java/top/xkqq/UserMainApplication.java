package top.xkqq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.xkqq.mapper")
public class UserMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMainApplication.class, args);
    }

}
