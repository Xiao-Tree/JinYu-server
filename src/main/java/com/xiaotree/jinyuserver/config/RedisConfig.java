package com.xiaotree.jinyuserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonSerializer = new GenericJackson2JsonRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationSerializer = new JdkSerializationRedisSerializer();

        template.setConnectionFactory(connectionFactory);

        template.setDefaultSerializer(genericJackson2JsonSerializer);
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jdkSerializationSerializer);

        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jdkSerializationSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
