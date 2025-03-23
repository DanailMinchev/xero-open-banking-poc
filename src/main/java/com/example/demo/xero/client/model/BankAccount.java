package com.example.demo.xero.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BankAccount {

    private String code;
    private String name;
    private String accountId;
    private String bankAccountNumber;

}
