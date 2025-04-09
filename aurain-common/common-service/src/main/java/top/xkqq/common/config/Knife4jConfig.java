package top.xkqq.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi webApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("web-api")         // 分组名称
                .pathsToMatch("/api/**")  // 接口请求路径规则
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi(){ // 创建一个 api 接口的分组
        return GroupedOpenApi.builder()
                .group("sysRole")     //分组名称
                .pathsToMatch("/admin/system/sysRole/**")  // 接口请求路径规则
                .pathsToMatch("/admin/system/index/**")
                .build();
    }

    @Bean
    public GroupedOpenApi indexApi(){
        return GroupedOpenApi.builder()
                .group("index")
                .pathsToMatch("/admin/system/index/**")
                .build();
    }

    /**
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Aurain好物API接口文档")
                        .version("1.0")
                        .description("Aurain好物API接口文档")
                        .contact(new Contact().name("ziyu")));// 设定作者
    }

}
