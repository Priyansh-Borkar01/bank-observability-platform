package com.npst.loggingapi.controller;

import com.npst.loggingapi.dto.LogRequestDto;
import com.npst.loggingapi.dto.LogResponseDto;
import com.npst.loggingapi.service.LoggingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/logs")
public class LoggingController {

    private final LoggingService service;

    public LoggingController(LoggingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LogResponseDto ingest(
            @Valid @RequestBody LogRequestDto request) {

        Long id = service.save(request);

        return new LogResponseDto("SUCCESS", id);
    }
}