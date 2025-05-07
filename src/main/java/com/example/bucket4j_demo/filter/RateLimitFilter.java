package com.example.bucket4j_demo.filter;

import com.example.bucket4j_demo.bucket4j.RateLimitManager;
import com.example.bucket4j_demo.common.authentication.AuthenticationContext;
import com.example.bucket4j_demo.common.request.RequestContext;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class RateLimitFilter implements Filter {

    private final RateLimitManager rateLimitManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String memberId = AuthenticationContext.getMemberId();
        String clientIp = request.getRemoteAddr();
//        String clientIp = httpRequest.getHeader("X-Forwarded-For"); // when existing reverse proxy like NGINX
        boolean isAuthenticated = (memberId != null);
        if (isAuthenticated) {
            log.info("[{}] [RateLimitFilter] memberId : {}", RequestContext.getRequestId(), memberId);
        } else {
            log.info("[{}] [RateLimitFilter] clientIp : {}", RequestContext.getRequestId(), clientIp);
        }

        String key = isAuthenticated ? "member:" + memberId : "ip:" + clientIp;
        Bucket bucket = rateLimitManager.resolveBucket(key, isAuthenticated);

        long availableTokens = bucket.getAvailableTokens();
        log.info("[{}] [RateLimitFilter] tokens : {}", RequestContext.getRequestId(), availableTokens);

        String path = ((HttpServletRequest) request).getRequestURI();
        int tokensToConsume = getTokensToConsume(path);

        if (bucket.tryConsume(tokensToConsume)) {
            log.info("[{}] [RateLimitFilter] {} token was consumed, token : {}", RequestContext.getRequestId(), tokensToConsume, bucket.getAvailableTokens());
            chain.doFilter(request, response);
        } else {
            log.info("[{}] [RateLimitFilter] token is insufficient, token : {}", RequestContext.getRequestId(), bucket.getAvailableTokens());
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.getWriter().write("Too Many Requests");
        }
    }

    private int getTokensToConsume(String path) {
        return path.startsWith("/api/auth") ? 2 : 1; // consume 2 tokens when call auth api
    }

}
