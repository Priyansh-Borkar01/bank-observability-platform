package com.npst.observability.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.observability.schema.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonLoggerImpl implements CommonLogger {

    private static final Logger log =
            LoggerFactory.getLogger(CommonLoggerImpl.class);

    private final ObjectMapper mapper;

    @Value("${spring.application.name}")
    private String serviceName;

    public CommonLoggerImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void logApplication(String message,
                               Map<String, Object> metadata) {

        LogEvent event = new LogEvent();

        event.setTraceId(MDC.get("traceId"));
        event.setServiceName(serviceName);
        event.setEventType(EventType.APPLICATION);
        event.setLevel(LogLevel.INFO);
        event.setMessage(message);
        event.setMetadata(metadata);

        write(event);
    }

    @Override
    public void logAudit(String actorId,
                         String actorType,
                         AuditAction action,
                         String entity,
                         String entityId,
                         String description) {

        AuditEvent event = new AuditEvent();

        event.setTraceId(MDC.get("traceId"));
        event.setServiceName(serviceName);
        event.setLevel(LogLevel.INFO);

        event.setActorId(actorId);
        event.setActorType(actorType);
        event.setAction(action);
        event.setEntity(entity);
        event.setEntityId(entityId);
        event.setDescription(description);

        write(event);
    }

    @Override
    public void logError(String message,
                         Exception exception) {

        ErrorEvent event = new ErrorEvent();

        event.setTraceId(MDC.get("traceId"));
        event.setServiceName(serviceName);
        event.setMessage(message);
        event.setException(exception.getClass().getSimpleName());
        event.setStackTrace(exception.getMessage());

        write(event);
    }

    private void write(Object event) {
        try {
            log.info(mapper.writeValueAsString(event));
        } catch (Exception e) {
            log.error("Failed to serialize log event", e);
        }
    }
}