package com.npst.observability.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.observability.audit.registry.AuditAction;
import com.npst.observability.audit.schema.AuditEvent;
import com.npst.observability.client.LoggingClient;
import com.npst.observability.config.bank.BankResolver;
import com.npst.observability.schema.ErrorEvent;
import com.npst.observability.schema.EventType;
import com.npst.observability.schema.LogEvent;
import com.npst.observability.schema.LogLevel;
import com.npst.observability.util.MaskingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommonLoggerImpl implements CommonLogger {

    private static final Logger log =
            LoggerFactory.getLogger(CommonLoggerImpl.class);

    private final ObjectMapper mapper;
    private final BankResolver bankResolver;
    private final LoggingClient loggingClient;

    @Value("${spring.application.name}")
    private String serviceName;

    public CommonLoggerImpl(ObjectMapper mapper,
                            BankResolver bankResolver,
                            LoggingClient loggingClient) {
        this.mapper = mapper;
        this.bankResolver = bankResolver;
        this.loggingClient = loggingClient;
    }

    /* -------------------- Metadata Masking -------------------- */

    private Map<String, Object> sanitizeMetadata(Map<String, Object> metadata) {

        if (metadata == null) {
            return Map.of();
        }

        Map<String, Object> sanitized = new HashMap<>();

        metadata.forEach((key, value) -> {

            if (value == null) {
                sanitized.put(key, null);
                return;
            }

            String field = key.toLowerCase();
            String text = value.toString();

            switch (field) {

                case "accountnumber":
                case "account":
                    sanitized.put(key, MaskingUtil.maskAccountNumber(text));
                    break;

                case "mobile":
                case "mobilenumber":
                    sanitized.put(key, MaskingUtil.maskMobile(text));
                    break;

                case "pan":
                    sanitized.put(key, MaskingUtil.maskPan(text));
                    break;

                default:
                    sanitized.put(key, value);
            }

        });

        return sanitized;
    }

    /* -------------------- Application Log -------------------- */

    @Override
    public void logApplication(String message,
                               Map<String, Object> metadata) {

        try {

            LogEvent event = new LogEvent();

            event.setTimestamp(Instant.now());
            event.setLevel(LogLevel.INFO);
            event.setEventType(EventType.APPLICATION);

            event.setMessage(message);
            event.setTraceId(MDC.get("traceId"));

            event.setBankCode(bankResolver.getCode());
            event.setEnvironment(bankResolver.getEnvironment());
            event.setService(serviceName);

            event.setMetadata(sanitizeMetadata(metadata));

            log.info(mapper.writeValueAsString(event));

            loggingClient.send(event);

        } catch (Exception e) {
            log.error("Application logging failed", e);
        }
    }

    /* -------------------- Business Audit -------------------- */

    @Override
    public void audit(String actorId,
                      String actorType,
                      AuditAction action,
                      String entity,
                      String entityId,
                      String description) {

        try {

            AuditEvent event = new AuditEvent();

            event.setActorId(actorId);
            event.setActorType(actorType);

            event.setAction(action);
            event.setEntity(entity);
            event.setEntityId(entityId);

            event.setDescription(description);

            event.setTraceId(MDC.get("traceId"));
            event.setBankCode(bankResolver.getCode());
            event.setEnvironment(bankResolver.getEnvironment());
            event.setService(serviceName);

            log.info(mapper.writeValueAsString(event));

            // Audit logs will be sent through AuditAspect + AuditClient
            // Do not use LoggingClient here.

        } catch (Exception e) {
            log.error("Audit logging failed", e);
        }
    }

    /* -------------------- Error Log -------------------- */

    @Override
    public void error(String message, Exception ex) {

        try {

            ErrorEvent event = new ErrorEvent();

            event.setTimestamp(Instant.now());
            event.setLevel(LogLevel.ERROR);
            event.setEventType(EventType.ERROR);

            event.setMessage(message);
            event.setTraceId(MDC.get("traceId"));

            event.setBankCode(bankResolver.getCode());
            event.setEnvironment(bankResolver.getEnvironment());
            event.setService(serviceName);

            event.setException(ex.getClass().getSimpleName());
            event.setStackTrace(ex.getMessage());

            log.error(mapper.writeValueAsString(event));

            loggingClient.send(event);

        } catch (Exception e) {
            log.error("Error logging failed", e);
        }
    }
}