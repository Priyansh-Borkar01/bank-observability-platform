package com.npst.observability.audit.aspect;

import com.npst.observability.audit.annotation.AuditLog;
import com.npst.observability.audit.client.AuditClient;
import com.npst.observability.audit.context.AuditContext;
import com.npst.observability.audit.context.AuditContextHolder;
import com.npst.observability.audit.schema.AuditEvent;
import com.npst.observability.config.bank.BankResolver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.UUID;

@Aspect
@Component
public class AuditAspect {

    private final AuditClient auditClient;
    private final BankResolver bankResolver;

    public AuditAspect(AuditClient auditClient,
                       BankResolver bankResolver) {
        this.auditClient = auditClient;
        this.bankResolver = bankResolver;
    }


    @Value("${spring.application.name}")
    private String serviceName;

    @AfterReturning("@annotation(auditLog)")
    public void captureAudit(JoinPoint joinPoint,
                             AuditLog auditLog) {
        //System.out.println("AOP EXECUTED");
        AuditContext context = AuditContextHolder.get();

        if (context == null) {
            return;
        }

        AuditEvent event = new AuditEvent();

        event.setTraceId(context.getTraceId());

        event.setActorId(context.getActorId());
        event.setActorType(context.getActorType());
        event.setCustomerId(context.getCustomerId());
        event.setModule(auditLog.module());

        event.setAction(auditLog.action());

        event.setEntity(auditLog.entity());
        event.setEntityId(auditLog.entityId());
        event.setDescription(auditLog.description());

        event.setDeviceId(context.getDeviceId());
        event.setDeviceType(context.getDeviceType());
        event.setIpAddress(context.getIpAddress());
        event.setMobileNumber(context.getMobileNumber());

        event.setBankCode(bankResolver.getCode());
        event.setEnvironment(bankResolver.getEnvironment());
        event.setService(serviceName);

        event.setTimestamp(Instant.now().toString());

        auditClient.send(event);
    }
}