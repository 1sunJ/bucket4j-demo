package com.example.bucket4j_demo.bucket4j;

import io.github.bucket4j.Bandwidth;

import java.time.Duration;

public class SimpleRateLimitPolicy {

    private static final long AUTH_CAPACITY = 20L; // Maximum number of tokens for authenticated user
    private static final long ANON_CAPACITY = 10L; // Maximum number of tokens for anonymous user
    private static final long AUTH_REFILL_TOKENS = 20L;
    private static final long ANON_REFILL_TOKENS = 10L;
    private static final Duration REFILL_PERIOD = Duration.ofMinutes(1L);

    public static Bandwidth getBandwidth(boolean isAuthenticated) {
        long capacity = isAuthenticated ? AUTH_CAPACITY : ANON_CAPACITY;
        long refillTokens = isAuthenticated ? AUTH_REFILL_TOKENS : ANON_REFILL_TOKENS;

        return Bandwidth.builder()
                .capacity(capacity)
                .refillIntervally(refillTokens, REFILL_PERIOD) // refill a refillToken every period / refillTokens
//                .refillGreedy(refillTokens, REFILL_PERIOD) // refill refillTokens at once periodically
                .build();
    }

}
