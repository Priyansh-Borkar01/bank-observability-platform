package com.npst.observability.config.bank;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BankProperties.class)
public class BankConfiguration {
}