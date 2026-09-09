package com.npst.loggingapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class EnrichedLogDto {

    private Instant timestamp;

    private String bankCode;
    private String environment;
    private String service;

    private String traceId;
    private String level;
    private String eventType;
    private String message;

    private Map<String, Object> metadata;
}
