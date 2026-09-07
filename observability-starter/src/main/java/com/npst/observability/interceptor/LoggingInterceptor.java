package com.npst.observability.interceptor;

import com.npst.observability.client.AuditClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.Instant;
import com.npst.observability.audit.AuditEvent;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingInterceptor.class);

    private static final String START_TIME = "startTime";
    private final AuditClient auditClient;

    public LoggingInterceptor(AuditClient auditClient) {
        this.auditClient = auditClient;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = (String) request.getAttribute("traceId");
        request.setAttribute(START_TIME, System.currentTimeMillis());
        log.info("Request Started : method={} uri = {} traceId={}", request.getMethod(), request.getRequestURI(), traceId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        System.out.println("AFTER COMPLETION: " + request.getRequestURI());
        long start = (Long) request.getAttribute(START_TIME);
        long duration = System.currentTimeMillis() - start;
        if (request.getRequestURI().equals("/api/v1/audit")) {
            return;
        }

        String traceId = (String) request.getAttribute("traceId");

        log.info("Request Completed : status={} duration={}ms traceId={}",
                response.getStatus(),
                duration,
                traceId);

        AuditEvent event = AuditEvent.builder()
                .traceId(traceId)
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .status(response.getStatus())
                .duration(duration)
                .timestamp(Instant.now().toString())
                .build();

        auditClient.send(event);
    }
}