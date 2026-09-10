package com.npst.observability.controller;
import com.npst.observability.audit.annotation.AuditLog;
import com.npst.observability.audit.registry.AuditAction;
import com.npst.observability.audit.registry.AuditModule;
import com.npst.observability.schema.AuditEvent;
import com.npst.observability.logger.CommonLogger;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final CommonLogger commonLogger;

    public HelloController(CommonLogger commonLogger) {
        this.commonLogger = commonLogger;
    }
    @AuditLog(
            module = AuditModule.ACCOUNT,
            action = AuditAction.BALANCE_VIEW,
            entity = "HELLO_API",
            entityId = "HELLO-001",
            description = "Hello endpoint invoked"
    )
    @GetMapping("/hello")
    public String hello() {

        commonLogger.logApplication(
                "Hello endpoint invoked",
                Map.of(
                        "module", "HELLO",
                        "version", "1.0"
                )
        );


        return "Hello API Success";
    }
}