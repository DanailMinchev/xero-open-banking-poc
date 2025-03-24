package com.example.demo.xero.client;

import com.example.demo.xero.client.model.BankAccount;
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

    public List<BankAccount> getBankAccounts(String xeroTenantId) throws IOException {
        String whereClause = "Status==\"ACTIVE\" && Type==\"BANK\"";
        String orderClause = "Name ASC";

        return xeroClientWrapper.getAccounts(xeroTenantId, null, whereClause, orderClause)
                .stream()
                .map(account -> BankAccount.builder()
                        .code(account.getCode())
                        .name(account.getName())
                        .accountId(account.getAccountID().toString())
                        .bankAccountNumber(account.getBankAccountNumber())
                        .build())
                .toList();
    }

    public void createBankTransaction(String xeroTenantId, String accountId) throws IOException {
        xeroClientWrapper.createBankTransaction(xeroTenantId, accountId);
    }

    public void getConnections(String xeroTenantId, Integer page, Integer pageSize) throws IOException {
        xeroClientWrapper.getConnections(xeroTenantId, page, pageSize);
    }

}
