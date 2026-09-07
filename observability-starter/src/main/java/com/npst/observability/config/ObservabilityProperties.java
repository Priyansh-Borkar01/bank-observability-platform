package com.npst.observability.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "observability")
public class ObservabilityProperties {
    private String bankName;
    private String environment;
    private String serviceName;

    private boolean serviceEnabled;
    private boolean telemetryEnabled;
    private boolean auditEnabled;

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getEnvironment() {
        return environment;
    }
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public boolean isServiceEnabled() {
        return serviceEnabled;
    }
    public void setServiceEnabled(boolean serviceEnabled) {
        this.serviceEnabled = serviceEnabled;

    }
    public boolean isTelemetryEnabled() {
        return telemetryEnabled;
    }
    public void setTelemetryEnabled(boolean telemetryEnabled) {
        this.telemetryEnabled = telemetryEnabled;
    }
    public boolean isAuditEnabled() {
        return auditEnabled;
    }
    public void setAuditEnabled(boolean auditEnabled) {
        this.auditEnabled = auditEnabled;
    }


}
