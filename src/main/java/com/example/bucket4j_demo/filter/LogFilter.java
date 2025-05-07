package com.example.bucket4j_demo.filter;

import com.example.bucket4j_demo.common.request.RequestContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestId = generateId();
        RequestContext.setRequestId(requestId);
        LocalDateTime startTime = LocalDateTime.now();

        // before
        log.info("[{}] [LogFilter] start : {} {} {}",
                requestId,
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                startTime
        );

        try {
            chain.doFilter(request, response);
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            long durationMs = Duration.between(startTime, endTime).toMillis();

            // after
            log.info("[{}] [LogFilter] end : {} ms", requestId, durationMs);

            RequestContext.clear();
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
