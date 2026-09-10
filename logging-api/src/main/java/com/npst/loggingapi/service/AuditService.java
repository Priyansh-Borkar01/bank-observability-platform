package com.npst.loggingapi.service;

import com.npst.loggingapi.entity.AuditLogEntity;
import com.npst.loggingapi.repository.AuditLogRepository;
import com.npst.observability.audit.schema.AuditEvent;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;

@Service
public class AuditService {

    private final AuditLogRepository repository;

    public AuditService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void save(AuditEvent event) throws Exception {


        if (repository.existsByEventHash(event.getEventHash())) {
            return;   // Ignore duplicate event
        }
        String hash = generateEventHash(event);
        if (repository.existsByEventHash(hash)) {
            return; // duplicate request
        }

        AuditLogEntity entity = AuditLogEntity.builder()
                .traceId(event.getTraceId())
                .eventHash(hash)

                .actorId(event.getActorId())
                .actorType(event.getActorType())
                .customerId(event.getCustomerId())

                .module(event.getModule().name())
                .action(event.getAction().name())

                .entity(event.getEntity())
                .entityId(event.getEntityId())
                .description(event.getDescription())

                .deviceId(event.getDeviceId())
                .deviceType(event.getDeviceType())
                .ipAddress(event.getIpAddress())
                .mobileNumber(event.getMobileNumber())

                .statusCode(event.getStatusCode())
                .responseMessage(event.getResponseMessage())

                .bankCode(event.getBankCode())
                .environment(event.getEnvironment())

                .createdAt(Instant.now())
                .build();

        repository.save(entity);
    }

    private String generateEventHash(AuditEvent event) throws Exception {

        String payload = String.join("|",
                event.getCustomerId(),
                event.getModule().name(),
                event.getAction().name(),
                event.getEntity(),
                event.getEntityId()
        );

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(payload.getBytes());

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();
    }
}