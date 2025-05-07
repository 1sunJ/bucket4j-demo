package com.example.bucket4j_demo.filter;

import com.example.bucket4j_demo.common.authentication.AuthenticationContext;
import com.example.bucket4j_demo.common.request.RequestContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Order(2)
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String memberId = httpRequest.getHeader("Authorization"); // -> memberId
        AuthenticationContext.setMemberId(memberId);
        log.info("[{}] [AuthenticationFilter] memberId ; {}", RequestContext.getRequestId(), memberId);

        try {
            chain.doFilter(request, response);
        } finally {
            AuthenticationContext.clear();
        }
    }

}
