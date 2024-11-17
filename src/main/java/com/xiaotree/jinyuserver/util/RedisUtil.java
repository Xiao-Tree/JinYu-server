package com.xiaotree.jinyuserver.util;

import com.xiaotree.jinyuserver.handler.JwtAuthenticationToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.MapSession;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisUtil {
    private final RedisTemplate<String, Object> template;

    public RedisUtil(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public void set(String key, String value) {
        this.template.opsForValue().set(key, value, 5L);
    }

    public Object get(String key) {
        return this.template.opsForValue().get(key);
    }

    public Set<String> getAllHashKey() {
        return this.template.keys("spring:session:sessions:*");
    }

    public HashMap<String, Object> getAllLoginUser() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        getAllHashKey().forEach(key -> {
            Object loginUser = this.template.opsForHash().get(key,
                    "sessionAttr:SPRING_SECURITY_CONTEXT");
            map.put(key, loginUser);
        });
        return map;
    }
}
