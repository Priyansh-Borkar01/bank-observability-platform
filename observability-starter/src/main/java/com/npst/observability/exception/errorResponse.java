package com.npst.observability.exception;

import java.time.LocalDateTime;

public class errorResponse {
    private String traceId;
    private String errorCode;
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public errorResponse() {
    }
    public errorResponse(String traceId, String errorCode, String message, int status, LocalDateTime timestamp) {
        this.traceId = traceId;
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
