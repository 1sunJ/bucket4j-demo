package com.example.bucket4j_demo.bucket4j;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimitManager {

    private final ProxyManager<String> proxyManager;

    /**
     * find existing or create new bucket by key
     * key -> IP or member_id ...
     */
    public Bucket resolveBucket(String key, boolean isAuthenticated) {
        Bandwidth limit = SimpleRateLimitPolicy.getBandwidth(isAuthenticated);

        return proxyManager.builder()
                .build(key, () -> BucketConfiguration.builder()
                        .addLimit(limit)
                        .build()
                );
    }

}
