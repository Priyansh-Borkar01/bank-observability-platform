package com.npst.loggingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogResponseDto {

    private String status;
    private Long logId;
}