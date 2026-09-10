package com.npst.observability.audit.client;

import com.npst.observability.audit.schema.AuditEvent;
import com.npst.observability.config.ObservabilityProperties;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AuditClient {

    private final RestTemplate restTemplate;
    private final ObservabilityProperties properties;

    public AuditClient(RestTemplate restTemplate,
                       ObservabilityProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public void send(AuditEvent event) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuditEvent> request =
                new HttpEntity<>(event, headers);
       // System.out.println("AUDIT SENT -> " + event);
        restTemplate.exchange(
                properties.getAudit().getEndpoint(),
                HttpMethod.POST,
                request,
                String.class
        );
    }
}