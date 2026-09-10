package com.npst.observability.config;

import com.npst.observability.filter.AuditFilter;
import com.npst.observability.filter.TraceFilter;
import com.npst.observability.interceptor.LoggingInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    public WebMvcConfig(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterRegistration(
            TraceFilter traceFilter) {

        FilterRegistrationBean<TraceFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(traceFilter);
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<AuditFilter> auditFilterRegistration(
            AuditFilter auditFilter) {

        FilterRegistrationBean<AuditFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(auditFilter);
        bean.setOrder(2);
        return bean;
    }
}