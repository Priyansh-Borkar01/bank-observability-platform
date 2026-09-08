package com.npst.observability.controller;

import com.npst.observability.schema.AuditAction;
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

    @GetMapping("/hello")
    public String hello() {

        commonLogger.logApplication(
                "Hello endpoint invoked",
                Map.of(
                        "module", "HELLO",
                        "version", "1.0"
                )
        );

        commonLogger.audit(
                "SYSTEM",
                "SERVICE",
                AuditAction.VIEW_CUSTOMER,
                "HELLO_API",
                "HELLO-001",
                "Hello endpoint invoked"
        );

        return "Hello API Success";
    }
}