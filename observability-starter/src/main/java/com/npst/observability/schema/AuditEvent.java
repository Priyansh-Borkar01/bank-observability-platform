package com.npst.observability.schema;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {

    private String traceId;

    private String method;

    private String uri;

    private Integer status;

    private Long duration;

    private String timestamp;

}