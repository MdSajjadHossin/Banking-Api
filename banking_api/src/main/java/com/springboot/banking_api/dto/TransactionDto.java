package com.springboot.banking_api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TransactionDto {

    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}
