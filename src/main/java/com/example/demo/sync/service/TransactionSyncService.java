package com.example.demo.sync.service;

import com.example.demo.xero.service.XeroBankTransactionImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionSyncService {

    private final XeroBankTransactionImportService xeroBankTransactionImportService;

    public void syncTransactions() {
        // TODO: retrieve not processed transactions from a database
        // TODO: import them into Xero
        xeroBankTransactionImportService.importBankTransactions();
    }

}
