package com.npst.observability.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.observability.client.LoggingClient;
import com.npst.observability.config.bank.BankResolver;
import com.npst.observability.schema.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonLoggerImpl implements CommonLogger {

    private static final Logger log =
            LoggerFactory.getLogger(CommonLoggerImpl.class);

    private final ObjectMapper mapper;
    private final BankResolver bankResolver;

    private final LoggingClient loggingClient;

    public CommonLoggerImpl(ObjectMapper mapper,
                            BankResolver bankResolver,
                            LoggingClient loggingClient) {
        this.mapper = mapper;
        this.bankResolver = bankResolver;
        this.loggingClient = loggingClient;
    }

    @Override
    public void logApplication(String message,
                               Map<String, Object> metadata) {

        try {
            LogEvent event = new LogEvent();

            event.setLevel(LogLevel.INFO);
            event.setEventType(EventType.APPLICATION);
            event.setMessage(message);
            event.setTraceId(MDC.get("traceId"));

            event.setBankCode(bankResolver.getCode());
            event.setEnvironment(bankResolver.getEnvironment());
            event.setService(bankResolver.getServiceName());

            event.setMetadata(metadata);

            log.info(mapper.writeValueAsString(event));
            loggingClient.send(event);

        } catch (Exception e) {
            log.error("Application logging failed", e);
        }
    }

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
            event.setService(bankResolver.getServiceName());


            log.info(mapper.writeValueAsString(event));
            loggingClient.send(event);

        } catch (Exception e) {
            log.error("Audit logging failed", e);
        }
    }

    @Override
    public void error(String message, Exception ex) {

        try {
            ErrorEvent event = new ErrorEvent();

            event.setLevel(LogLevel.ERROR);
            event.setEventType(EventType.ERROR);
            event.setMessage(message);
            event.setTraceId(MDC.get("traceId"));
            event.setException(ex.getClass().getSimpleName());
            event.setStackTrace(ex.getMessage());

            log.error(mapper.writeValueAsString(event));


        } catch (Exception e) {
            log.error("Error logging failed", e);
        }
    }
}