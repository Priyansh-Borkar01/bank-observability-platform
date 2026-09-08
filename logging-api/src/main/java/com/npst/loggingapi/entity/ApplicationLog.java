package com.npst.loggingapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "application_logs")
public class ApplicationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String traceId;

    @Column(nullable = false)
    private String bankCode;

    @Column(nullable = false)
    private String environment;

    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String level;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "JSON")
    private String metadata;

    private LocalDateTime createdAt;
}