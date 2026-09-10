package com.npst.loggingapi.controller;

import com.npst.loggingapi.dto.AuditSearchRequest;
import com.npst.loggingapi.dto.AuditSearchResponse;
import com.npst.loggingapi.dto.PageResponse;
import com.npst.loggingapi.service.AuditService;
import com.npst.observability.audit.schema.AuditEvent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public void ingest(@RequestBody AuditEvent event) throws Exception {
        auditService.save(event);
    }

    @GetMapping("/search")
    public PageResponse<AuditSearchResponse> search(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String traceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        AuditSearchRequest request = new AuditSearchRequest();
        request.setCustomerId(customerId);
        request.setModule(module);
        request.setAction(action);
        request.setTraceId(traceId);

        return auditService.search(request, page, size);
    }
}