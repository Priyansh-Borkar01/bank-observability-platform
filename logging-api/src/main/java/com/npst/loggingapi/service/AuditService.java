package com.npst.loggingapi.service;

import com.npst.loggingapi.dto.AuditSearchRequest;
import com.npst.loggingapi.dto.AuditSearchResponse;
import com.npst.loggingapi.dto.PageResponse;
import com.npst.loggingapi.entity.AuditLogEntity;
import com.npst.loggingapi.repository.AuditLogRepository;
import com.npst.observability.audit.schema.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // =========================
    // SAVE AUDIT
    // =========================
    public void save(AuditEvent event) throws Exception {

        event.setEventHash(generateEventHash(event));

        if (auditLogRepository.existsByEventHash(event.getEventHash())) {
            return;
        }

        AuditLogEntity entity = AuditLogEntity.builder()
                .id(UUID.randomUUID().toString())
                .traceId(event.getTraceId())
                .customerId(event.getCustomerId())
                .actorId(event.getActorId())
                .actorType(event.getActorType())
                .module(event.getModule().name())
                .action(event.getAction().name())
                .entity(event.getEntity())
                .entityId(event.getEntityId())
                .description(event.getDescription())
                .deviceId(event.getDeviceId())
                .deviceType(event.getDeviceType())
                .ipAddress(event.getIpAddress())
                .mobileNumber(event.getMobileNumber())
                .bankCode(event.getBankCode())
                .environment(event.getEnvironment())
                .responseMessage(event.getResponseMessage())
                .statusCode(event.getStatusCode())
                .eventHash(event.getEventHash())
                .createdAt(Instant.now())
                .build();

        auditLogRepository.save(entity);
    }

    // =========================
    // SEARCH AUDITS (PAGINATED)
    // =========================
    public PageResponse<AuditSearchResponse> search(
            AuditSearchRequest request,
            int page,
            int size) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<AuditLogEntity> logs = auditLogRepository.search(
                request.getCustomerId(),
                request.getModule(),
                request.getAction(),
                request.getTraceId(),
                pageable
        );

        List<AuditSearchResponse> response = logs.getContent()
                .stream()
                .map(log -> AuditSearchResponse.builder()
                        .traceId(log.getTraceId())
                        .customerId(log.getCustomerId())
                        .module(log.getModule())
                        .action(log.getAction())
                        .entity(log.getEntity())
                        .description(log.getDescription())
                        .createdAt(log.getCreatedAt())
                        .build())
                .toList();

        return PageResponse.<AuditSearchResponse>builder()
                .content(response)
                .page(logs.getNumber())
                .size(logs.getSize())
                .totalElements(logs.getTotalElements())
                .totalPages(logs.getTotalPages())
                .first(logs.isFirst())
                .last(logs.isLast())
                .build();
    }

    // =========================
    // SHA-256 HASH
    // =========================
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

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}