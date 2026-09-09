package com.npst.loggingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean("lokiRestTemplate")
    public RestTemplate lokiRestTemplate() {
        return new RestTemplate();
    }
}