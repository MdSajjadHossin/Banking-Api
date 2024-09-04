package com.springboot.banking_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditDebitRequest {

    private String accountNumber;
    private BigDecimal amount;
}
