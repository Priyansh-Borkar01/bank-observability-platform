package com.npst.observability.controller;

import com.npst.observability.audit.AuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private static final Logger log =
            LoggerFactory.getLogger(HelloController.class);

    private final RestTemplate restTemplate;

    public HelloController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello")
    public String hello() {

        log.info("HELLO_API_CALLED");

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