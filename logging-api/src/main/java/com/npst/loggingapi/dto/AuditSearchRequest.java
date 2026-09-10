package com.npst.loggingapi.dto;

import lombok.Data;

@Data
public class AuditSearchRequest {

    private String customerId;
    private String module;
    private String action;
    private String traceId;

}