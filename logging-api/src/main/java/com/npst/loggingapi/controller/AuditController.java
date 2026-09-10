package com.npst.loggingapi.controller;

import com.npst.loggingapi.service.AuditService;
import com.npst.observability.audit.schema.AuditEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<String> ingest(@RequestBody AuditEvent event)
            throws Exception {

        auditService.save(event);

        return ResponseEntity.ok("Audit log stored");
    }
}