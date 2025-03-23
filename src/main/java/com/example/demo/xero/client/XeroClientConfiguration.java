package com.example.demo.xero.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class XeroClientConfiguration {

    @Value("${app.xero.apiUrl}")
    private String clientId;

    @Bean
    public XeroClientFactory xeroClientFactory() {
        return new XeroClientFactory(clientId);
    }

}
