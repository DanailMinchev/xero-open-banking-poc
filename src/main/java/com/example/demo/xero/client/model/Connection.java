package com.example.demo.xero.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Connection {

    private String id;
    private String tenantId;

}
