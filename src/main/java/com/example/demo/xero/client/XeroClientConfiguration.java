package com.example.demo.xero.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class XeroClientConfiguration {

    @Value("${app.xero.apiUrl}")
    private String apiUrl;

    @Bean
    public XeroClientFactory xeroClientFactory() {
        String apiUrlWithoutTrailingSlash = apiUrl.replaceFirst("/*$", "");
        return new XeroClientFactory(apiUrlWithoutTrailingSlash);
    }

}
