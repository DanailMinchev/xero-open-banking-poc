package com.example.demo.xero.client;

import com.xero.api.ApiClient;
import com.xero.api.client.AccountingApi;
import com.xero.api.client.IdentityApi;
import com.xero.models.accounting.Account;
import com.xero.models.accounting.Accounts;
import com.xero.models.identity.Connection;
import lombok.RequiredArgsConstructor;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
class XeroClientWrapper {

    private final ApiClient rootPathApiClient;

    private final ApiClient apiXro2Point0PathApiClient;

    private final String accessToken;

    public List<Connection> getConnections() throws IOException {
        IdentityApi identityApi = new IdentityApi(rootPathApiClient);

        return identityApi.getConnections(accessToken, null);
    }

    public List<Account> getAccounts(String xeroTenantId,
                                     OffsetDateTime ifModifiedSince,
                                     String whereClause,
                                     String orderClause) throws IOException {
        AccountingApi accountingApi = new AccountingApi(apiXro2Point0PathApiClient);

        Accounts accounts = accountingApi.getAccounts(accessToken, xeroTenantId, ifModifiedSince, whereClause, orderClause);

        return accounts.getAccounts();
    }

}
