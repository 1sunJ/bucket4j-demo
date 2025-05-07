package com.example.bucket4j_demo.bucket4j;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisBucketCleaner {

    private final StringRedisTemplate redisTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void cleanBucketsInRedis() {
        redisTemplate.delete(redisTemplate.keys("member:*"));
        redisTemplate.delete(redisTemplate.keys("ip:*"));
        log.info("레디스에 있는 버킷들 비움 !!!");
    }

}
