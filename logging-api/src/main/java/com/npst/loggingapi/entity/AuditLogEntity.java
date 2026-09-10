package com.npst.loggingapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String traceId;
    private String eventHash;

    private String actorId;
    private String actorType;
    private String customerId;

    private String module;
    private String action;

    private String entity;
    private String entityId;

    @Column(length = 500)
    private String description;

    private String deviceId;
    private String deviceType;
    private String ipAddress;
    private String mobileNumber;

    private Integer statusCode;
    private String responseMessage;

    private String bankCode;
    private String environment;

    private Instant createdAt;
}