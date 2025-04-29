package com.example.bucket4j_demo.controller;

import com.example.bucket4j_demo.common.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GeneralController {

    @GetMapping("/sun")
    public void sun() {
        log.info("[{}] controller : sun", RequestContext.getRequestId());
    }

    @PostMapping("/jae")
    public void jae() {
        log.info("[{}] controller : jae", RequestContext.getRequestId());
    }

}
