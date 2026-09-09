package com.npst.loggingapi.controller;

import com.npst.loggingapi.client.LokiClient;
import com.npst.loggingapi.dto.EnrichedLogDto;
import com.npst.loggingapi.dto.LogRequestDto;
import com.npst.loggingapi.dto.LogResponseDto;
import com.npst.loggingapi.service.LogEnrichmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/logs")
public class LoggingController {

    private final LokiClient lokiClient;

    private final LogEnrichmentService enrichmentService;

    public LoggingController(LokiClient lokiClient, LogEnrichmentService enrichmentService) {
        this.lokiClient = lokiClient;
        this.enrichmentService = enrichmentService;
    }

    @PostMapping
    public LogResponseDto ingest(@Valid @RequestBody LogRequestDto request) throws Exception {

        EnrichedLogDto enriched = enrichmentService.enrich(request);

        lokiClient.push(enriched);

        return LogResponseDto.builder()
                .success(true)
                .traceId(enriched.getTraceId())
                .message("Application log accepted")
                .build();
    }
}