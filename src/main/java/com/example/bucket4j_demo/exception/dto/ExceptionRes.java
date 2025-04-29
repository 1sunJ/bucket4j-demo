package com.example.bucket4j_demo.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionRes {

    private int code;
    private String message;
    @Builder.Default
    private LocalDateTime occurredDateTime = LocalDateTime.now();

}
