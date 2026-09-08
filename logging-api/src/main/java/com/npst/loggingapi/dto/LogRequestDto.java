package com.npst.loggingapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class LogRequestDto {

    @NotBlank
    private String traceId;

    @NotBlank
    private String bankCode;

    @NotBlank
    private String environment;

    @NotBlank
    private String service;

    @NotBlank
    private String eventType;

    @NotBlank
    private String level;

    @NotBlank
    private String message;

    private Map<String, Object> metadata;
}