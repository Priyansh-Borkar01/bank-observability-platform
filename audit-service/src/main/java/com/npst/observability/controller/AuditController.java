package com.npst.observability.controller;

import com.npst.observability.audit.AuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuditController {

    private static final Logger log =
            LoggerFactory.getLogger(AuditController.class);
    @PostMapping("/audit")
    public void receiveAudit(@RequestBody AuditEvent event) {

        String traceId = MDC.get("traceId");

        log.info("AUDIT_EVENT_RECEIVED");
        log.info("traceId={}", traceId);
        log.info("method={} uri={} status={} duration={}ms",
                event.getMethod(),
                event.getUri(),
                event.getStatus(),
                event.getDuration());
    }
}