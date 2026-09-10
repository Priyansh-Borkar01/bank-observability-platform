package com.npst.observability.audit.context;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditContext {

    private String customerId;

    private String actorId;

    private String actorType;

    private String mobileNumber;

    private String deviceId;

    private String deviceType;

    private String ipAddress;

    private String traceId;

}