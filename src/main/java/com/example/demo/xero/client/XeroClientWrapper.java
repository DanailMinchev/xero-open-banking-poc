package com.example.demo.xero.client;

import com.xero.api.ApiClient;
import com.xero.api.client.IdentityApi;
import com.xero.models.identity.Connection;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
class XeroClientWrapper {

    private final ApiClient apiClient;

    private final String accessToken;

    public List<Connection> getConnections() throws IOException {
        IdentityApi identityApi = new IdentityApi(apiClient);

        return identityApi.getConnections(accessToken, null);
    }

}
