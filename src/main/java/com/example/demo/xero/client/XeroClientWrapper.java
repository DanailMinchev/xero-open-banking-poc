package com.example.demo.xero.client;

import com.xero.api.ApiClient;
import com.xero.api.client.AccountingApi;
import com.xero.api.client.IdentityApi;
import com.xero.models.accounting.Account;
import com.xero.models.accounting.Accounts;
import com.xero.models.accounting.BankTransaction;
import com.xero.models.accounting.BankTransactions;
import com.xero.models.accounting.LineItem;
import com.xero.models.identity.Connection;
import lombok.RequiredArgsConstructor;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    public void createBankTransaction(String xeroTenantId, String accountId) throws IOException {
        AccountingApi accountingApi = new AccountingApi(apiXro2Point0PathApiClient);

        Accounts accounts = accountingApi.getAccount(accessToken, xeroTenantId, UUID.fromString(accountId));
        Account firstAccount = accounts.getAccounts().getFirst();

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(firstAccount);
        bankTransaction.setType(BankTransaction.TypeEnum.SPEND);
        bankTransaction.lineItems(List.of(
                new LineItem()
//                        .accountCode("4400")
                        .accountCode("400")
//                        .accountCode(firstAccount.getCode()) // TODO: use the correct code for SPEND transaction
                        .taxType("NONE")
                        .description("Description here")
                        .quantity(1.0)
                        .unitAmount(10.0)
        ));

        BankTransactions bankTransactions = new BankTransactions();
        bankTransactions.addBankTransactionsItem(bankTransaction);

        BankTransactions bankTransactionsResponse = accountingApi.createBankTransactions(
                accessToken,
                xeroTenantId,
                bankTransactions,
                false,
                4,
                null
        );

        // TODO: fix transactions errors
        // - A Contact must be specified for this type of transaction
        // - Account code '400' is not a valid code for this document.
        BankTransaction createdBankTransaction = bankTransactionsResponse.getBankTransactions().getFirst();
    }

}
