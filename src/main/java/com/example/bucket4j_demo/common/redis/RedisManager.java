package com.example.bucket4j_demo.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean checkExistingData(String key) {
        return redisTemplate.hasKey(key);
    }

    public void setExData(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

}
