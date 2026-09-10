package com.npst.loggingapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditSearchResponse {

    private String traceId;
    private String customerId;
    private String module;
    private String action;
    private String entity;
    private String description;
    private LocalDateTime createdAt;

}