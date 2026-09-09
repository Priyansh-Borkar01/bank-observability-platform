package com.npst.loggingapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.loggingapi.dto.EnrichedLogDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class LokiClient {

        private final RestTemplate restTemplate;
        private final ObjectMapper mapper;

        public LokiClient(
                @Qualifier("lokiRestTemplate") RestTemplate restTemplate,
                ObjectMapper mapper) {

            this.restTemplate = restTemplate;
            this.mapper = mapper;
        }


    public void push(EnrichedLogDto log) throws Exception {

        String json = mapper.writeValueAsString(log);

        Map<String, Object> body = Map.of(
                "streams", List.of(
                        Map.of(
                                "stream", Map.of(
                                        "service", log.getService(),
                                        "level", log.getLevel(),
                                        "bank", log.getBankCode()
                                ),
                                "values", List.of(
                                        List.of(
                                                String.valueOf(Instant.now().toEpochMilli() * 1_000_000),
                                                json
                                        )
                                )
                        )
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.exchange(
                "http://localhost:3100/loki/api/v1/push",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class
        );
    }
}