package com.example.demo.sync.scheduling;

import com.example.demo.sync.service.TransactionSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionSyncScheduler {

    private final TransactionSyncService transactionSyncService;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void syncTransactions() {
        log.info("Started 'sync transactions' scheduled task");

        transactionSyncService.syncTransactions();

        log.info("Finished 'sync transactions' scheduled task");
    }

}
