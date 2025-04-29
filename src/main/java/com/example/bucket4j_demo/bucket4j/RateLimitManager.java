package com.example.bucket4j_demo.bucket4j;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimitManager {

    private final ProxyManager<String> proxyManager;

    private static final long CAPACITY = 50L; // Maximum number of tokens
    private static final long REFILL_TOKENS = 10L;
    private static final Duration REFILL_PERIOD = Duration.ofSeconds(10L);

    /**
     * find existing or new bucket by key
     * key -> IP or member_id ...
     */
    public Bucket resolveBucket(String key) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(CAPACITY)
                .refillGreedy(REFILL_TOKENS, REFILL_PERIOD)
                .build();

        return proxyManager.builder()
                .build(key, () -> BucketConfiguration.builder()
                        .addLimit(limit)
                        .build()
                );
    }

}
