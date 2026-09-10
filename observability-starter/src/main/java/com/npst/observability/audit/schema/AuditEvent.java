package com.npst.observability.audit.schema;

import com.npst.observability.audit.registry.AuditAction;
import com.npst.observability.audit.registry.AuditModule;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {

    // Identity
    private String auditId;
    private String traceId;
    private String eventHash;

    // Actor
    private String actorId;
    private String actorType;
    private String customerId;

    // Business
    private AuditModule module;
    private AuditAction action;
    private String entity;
    private String entityId;
    private String description;

    // Request Context
    private String deviceId;
    private String deviceType;
    private String ipAddress;
    private String mobileNumber;

    // Result
    private Integer statusCode;
    private String responseMessage;

    // Bank Metadata
    private String bankCode;
    private String environment;
    private String service;

    // Time
    private String timestamp;

}