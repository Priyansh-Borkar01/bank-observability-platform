package com.npst.observability.filter;

import com.npst.observability.audit.context.AuditContext;
import com.npst.observability.audit.context.AuditContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuditFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        AuditContext context = AuditContext.builder()
                .traceId(MDC.get("traceId"))
                .customerId(request.getHeader("X-Customer-Id"))
                .actorId(request.getHeader("X-Customer-Id"))
                .actorType("CUSTOMER")
                .deviceId(request.getHeader("X-Device-Id"))
                .deviceType(request.getHeader("X-Device-Type"))
                .mobileNumber(request.getHeader("X-Mobile"))
                .ipAddress(request.getRemoteAddr())
                .build();
       // System.out.println("AUDIT FILTER EXECUTED");
        AuditContextHolder.set(context);

        try {
            filterChain.doFilter(request, response);
        } finally {
            AuditContextHolder.clear();
        }
    }
}