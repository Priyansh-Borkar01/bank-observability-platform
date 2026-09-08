package com.npst.observability.config.bank;

import org.springframework.stereotype.Component;

@Component
public class BankResolver {

    private final BankProperties properties;

    public BankResolver(BankProperties properties) {
        this.properties = properties;
    }

    public String getCode() {
        return properties.getCode();
    }

    public String getName() {
        return properties.getName();
    }

    public String getEnvironment() {
        return properties.getEnvironment();
    }

    public String getRegion() {
        return properties.getRegion();
    }
}