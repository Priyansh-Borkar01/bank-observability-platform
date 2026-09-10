package com.npst.observability.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingInterceptor.class);

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String traceId = (String) request.getAttribute("traceId");

        request.setAttribute(START_TIME, System.currentTimeMillis());

        log.info("Request Started : method={} uri={} traceId={}",
                request.getMethod(),
                request.getRequestURI(),
                traceId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        if (request.getRequestURI().equals("/api/v1/audit")) {
            return;
        }

        long start = (Long) request.getAttribute(START_TIME);
        long duration = System.currentTimeMillis() - start;

        String traceId = (String) request.getAttribute("traceId");

        log.info("Request Completed : status={} duration={}ms traceId={}",
                response.getStatus(),
                duration,
                traceId);
    }
}