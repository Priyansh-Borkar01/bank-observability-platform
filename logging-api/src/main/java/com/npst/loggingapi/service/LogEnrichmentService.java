package com.npst.loggingapi.service;

import com.npst.loggingapi.dto.EnrichedLogDto;
import com.npst.loggingapi.dto.LogRequestDto;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogEnrichmentService{
    public EnrichedLogDto enrich(LogRequestDto request) {

        return EnrichedLogDto.builder()
                .timestamp(Instant.now())
                .bankCode(request.getBankCode())
                .environment(request.getEnvironment())
                .service(request.getService())
                .traceId(request.getTraceId())
                .level(request.getLevel())
                .eventType(request.getEventType())
                .message(request.getMessage())
                .metadata(request.getMetadata())
                .build();
    }
}