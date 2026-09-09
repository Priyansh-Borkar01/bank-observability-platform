package com.npst.loggingapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogResponseDto {

    private boolean success;
    private String traceId;
    private String message;
}