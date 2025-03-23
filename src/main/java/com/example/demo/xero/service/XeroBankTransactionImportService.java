package com.example.demo.xero.service;

import com.example.demo.xero.client.XeroClientFacade;
import com.example.demo.xero.client.XeroClientFactory;
import com.example.demo.xero.client.model.BankAccount;
import com.example.demo.xero.client.model.Connection;
import com.example.demo.xero.security.domain.XeroOAuth2AuthorizedClientEntity;
import com.example.demo.xero.security.repository.XeroOAuth2AuthorizedClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class XeroBankTransactionImportService {

    private final XeroOAuth2AuthorizedClientRepository xeroOAuth2AuthorizedClientRepository;
    private final XeroClientFactory xeroClientFactory;

    public void importBankTransactions() {
        XeroOAuth2AuthorizedClientEntity xeroOAuth2AuthorizedClientEntity =
                xeroOAuth2AuthorizedClientRepository.findAll().getFirst();

        XeroClientFacade xeroClientFacade = xeroClientFactory.getClientFor(xeroOAuth2AuthorizedClientEntity.getAccessToken());

        try {
            List<Connection> connections = xeroClientFacade.getConnections();

            Connection firstConnection = connections.getFirst();
            log.info("Connection: {}", firstConnection);

            List<BankAccount> bankAccounts = xeroClientFacade.getBankAccounts(firstConnection.getTenantId());
            log.info("Bank accounts: {}", bankAccounts);
        } catch (Exception exception) {
            log.info("Error calling Xero operation", exception);
        }
    }

}
