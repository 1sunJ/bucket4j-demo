package com.example.bucket4j_demo.controller;

import com.example.bucket4j_demo.common.request.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/register")
    public void register() {
        log.info("[{}] [controller] register", RequestContext.getRequestId());
    }

    @PostMapping("/login")
    public void login() {
        log.info("[{}] [controller] login", RequestContext.getRequestId());
    }

}
