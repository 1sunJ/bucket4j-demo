package com.example.bucket4j_demo.bucket4j;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RateLimitManager {

    private final ProxyManager<String> proxyManager;

    private static final long AUTH_CAPACITY = 50L; // Maximum number of tokens for authenticated user
    private static final long ANON_CAPACITY = 100L; // Maximum number of tokens for anonymous user
    private static final long AUTH_REFILL_TOKENS = 50L;
    private static final long ANON_REFILL_TOKENS = 100L;
    private static final Duration REFILL_PERIOD = Duration.ofMinutes(1L);

    /**
     * find existing or create new bucket by key
     * key -> IP or member_id ...
     */
    public Bucket resolveBucket(String key, boolean isAuthenticated) {
        long capacity = isAuthenticated ? AUTH_CAPACITY : ANON_CAPACITY;
        long refillTokens = isAuthenticated ? AUTH_REFILL_TOKENS : ANON_REFILL_TOKENS;

        // policy for rate limit
        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity)
                .refillGreedy(refillTokens, REFILL_PERIOD)
                .build();

        return proxyManager.builder()
                .build(key, () -> BucketConfiguration.builder()
                        .addLimit(limit)
                        .build()
                );
    }

}
