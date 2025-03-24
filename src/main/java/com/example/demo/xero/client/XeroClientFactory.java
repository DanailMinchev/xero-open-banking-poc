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
            XeroClientWrapper clientWrapper = new XeroClientWrapper(
                    createRootPathApiClient(),
                    createApiXro2Point0PathApiClient(),
                    createBankfeedsXro1Point0PathApiClient(),
                    token
            );
            return new XeroClientFacade(clientWrapper);
        });
    }

    private ApiClient createRootPathApiClient() {
        return new ApiClient(apiUrl, null, null, null, null);
    }

    private ApiClient createApiXro2Point0PathApiClient() {
        return new ApiClient(apiUrl + "/api.xro/2.0", null, null, null, null);
    }

    private ApiClient createBankfeedsXro1Point0PathApiClient() {
        return new ApiClient(apiUrl + "/bankfeeds.xro/1.0", null, null, null, null);
    }

}
