package com.npst.observability.client;

import com.npst.observability.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuditClient {
    private final RestTemplate restTemplate;
    public AuditClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void send(AuditEvent auditEvent) {
        try {
            restTemplate.postForEntity(
                    "http://localhost:8081/api/v1/audit",
                    auditEvent,
                    Void.class
            );
        }
        catch(Exception ignored)
        {

        }
    }
}
