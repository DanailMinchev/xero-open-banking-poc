package com.example.demo.xero.client;

import com.xero.api.ApiClient;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class XeroClientFactory {

    private final String apiUrl;

    private final Map<String, XeroClientFacade> clients = new ConcurrentHashMap<>();

    public XeroClientFacade getClientFor(String accessToken) {
        return clients.computeIfAbsent(accessToken, token -> {
            XeroClientWrapper clientWrapper = new XeroClientWrapper(createApiClient(), token);
            return new XeroClientFacade(clientWrapper);
        });
    }

    private ApiClient createApiClient() {
        return new ApiClient(apiUrl, null, null, null, null);
    }

}
