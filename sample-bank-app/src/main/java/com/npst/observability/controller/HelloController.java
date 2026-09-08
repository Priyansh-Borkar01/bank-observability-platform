package com.npst.observability.controller;

import com.npst.observability.audit.AuditEvent;
import com.npst.observability.logger.CommonLogger;
import org.slf4j.MDC;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final RestTemplate restTemplate;
    private final CommonLogger commonLogger;

    public HelloController(RestTemplate restTemplate,
                           CommonLogger commonLogger) {
        this.restTemplate = restTemplate;
        this.commonLogger = commonLogger;
    }

    @GetMapping("/hello")
    public String hello() {

        // Application log (reusable platform)
        commonLogger.logApplication(
                "Hello endpoint invoked",
                Map.of(
                        "module", "HELLO",
                        "version", "1.0"
                )
        );

        // Send audit event
        AuditEvent event = AuditEvent.builder()
                .method("GET")
                .uri("/api/v1/hello")
                .status(200)
                .duration(0)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Trace-Id", MDC.get("traceId"));

        HttpEntity<AuditEvent> request =
                new HttpEntity<>(event, headers);

        restTemplate.postForObject(
                "http://localhost:8081/api/v1/audit",
                request,
                String.class
        );

        return "Hello API Success";
    }
}