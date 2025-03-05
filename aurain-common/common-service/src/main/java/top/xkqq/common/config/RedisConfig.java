package top.xkqq.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂
        template.setConnectionFactory(factory);

        //设置 key 的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        //设置 value 的序列化方式
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Hash类型的key序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // Hash类型的value序列化方式
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 初始化RedisTemplate
        template.afterPropertiesSet();
        return template;
    }

}
