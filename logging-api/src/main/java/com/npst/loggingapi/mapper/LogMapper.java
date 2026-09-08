package com.npst.loggingapi.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npst.loggingapi.dto.LogRequestDto;
import com.npst.loggingapi.entity.ApplicationLog;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogMapper {

    private final ObjectMapper objectMapper;

    public LogMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ApplicationLog toEntity(LogRequestDto dto) {

        ApplicationLog log = new ApplicationLog();

        log.setTraceId(dto.getTraceId());
        log.setBankCode(dto.getBankCode());
        log.setEnvironment(dto.getEnvironment());
        log.setService(dto.getService());
        log.setEventType(dto.getEventType());
        log.setLevel(dto.getLevel());
        log.setMessage(dto.getMessage());

        try {
            log.setMetadata(
                    objectMapper.writeValueAsString(dto.getMetadata())
            );
        } catch (JsonProcessingException e) {
            log.setMetadata("{}");
        }

        log.setCreatedAt(LocalDateTime.now());

        return log;
    }
}