package com.npst.loggingapi.service;

import com.npst.loggingapi.dto.LogRequestDto;
import com.npst.loggingapi.entity.ApplicationLog;
import com.npst.loggingapi.mapper.LogMapper;
import com.npst.loggingapi.repository.ApplicationLogRepository;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    private final ApplicationLogRepository repository;
    private final LogMapper mapper;

    public LoggingService(ApplicationLogRepository repository,
                          LogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Long save(LogRequestDto dto) {

        ApplicationLog entity = mapper.toEntity(dto);

        ApplicationLog saved = repository.save(entity);

        return saved.getId();
    }
}