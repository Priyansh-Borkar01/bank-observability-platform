package com.npst.observability.config;

import com.npst.observability.audit.aspect.AuditAspect;
import com.npst.observability.audit.client.AuditClient;
import com.npst.observability.client.TraceRestTemplateInterceptor;
import com.npst.observability.config.bank.BankProperties;
import com.npst.observability.config.bank.BankResolver;
import com.npst.observability.filter.AuditFilter;
import com.npst.observability.filter.TraceFilter;
import com.npst.observability.interceptor.LoggingInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.npst.observability.config.bank.BankResolver;

import java.util.List;

@Configuration
@EnableConfigurationProperties({
        ObservabilityProperties.class,
        BankProperties.class
})
public class ObservabilityAutoConfiguration {

    @Bean
    public TraceRestTemplateInterceptor traceRestTemplateInterceptor() {
        return new TraceRestTemplateInterceptor();
    }

    @Bean
    public RestTemplate restTemplate(TraceRestTemplateInterceptor interceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of(interceptor));
        return restTemplate;
    }

    @Bean
    public AuditClient auditClient(RestTemplate restTemplate,
                                   ObservabilityProperties properties) {
        return new AuditClient(restTemplate, properties);
    }

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }
    @Bean
    public TraceFilter traceFilter() {
        return new TraceFilter();
    }

    @Bean
    public AuditFilter auditFilter() {
        return new AuditFilter();
    }
    @Bean
    public AuditAspect auditAspect(AuditClient auditClient,
                                   BankResolver bankResolver) {
        return new AuditAspect(auditClient, bankResolver);
    }

    @Bean
    public BankResolver bankResolver(BankProperties properties) {
        return new BankResolver(properties);
    }
}