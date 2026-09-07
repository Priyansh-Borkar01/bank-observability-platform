package com.npst.observability.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {

    private String traceId;
    private String method;
    private String uri;
    private int status;
    private long duration;
    private String timestamp;
}
