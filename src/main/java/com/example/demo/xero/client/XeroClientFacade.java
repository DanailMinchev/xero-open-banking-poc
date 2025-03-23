package com.example.demo.xero.client;

import com.example.demo.xero.client.model.Connection;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class XeroClientFacade {

    private final XeroClientWrapper xeroClientWrapper;

    public List<Connection> getConnections() throws IOException {
        List<com.xero.models.identity.Connection> xeroConnections = xeroClientWrapper.getConnections();

        return xeroConnections
                .stream()
                .map(xeroConnection -> Connection.builder()
                        .id(xeroConnection.getId().toString())
                        .tenantId(xeroConnection.getTenantId().toString())
                        .build())
                .toList();
    }

}
