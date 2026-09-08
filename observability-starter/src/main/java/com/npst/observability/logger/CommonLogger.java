package com.npst.observability.logger;

import com.npst.observability.schema.AuditAction;

import java.util.Map;

public interface CommonLogger {

    void logApplication(
            String message,
            Map<String, Object> metadata
    );

    void logAudit(
            String actorId,
            String actorType,
            AuditAction action,
            String entity,
            String entityId,
            String description
    );

    void logError(
            String message,
            Exception exception
    );
}