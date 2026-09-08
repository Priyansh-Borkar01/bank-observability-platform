package com.npst.observability.client;

import com.npst.observability.schema.LogEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoggingClient {

    private final RestTemplate restTemplate;

    @Value("${observability.logging.endpoint}")
    private String endpoint;

    public LoggingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void send(LogEvent event) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<LogEvent> request =
                    new HttpEntity<>(event, headers);

            restTemplate.postForEntity(endpoint, request, Void.class);

        } catch (Exception ignored) {
            // Never break the bank application if logging fails
        }
    }
}