package com.springboot.banking_api.service;

import com.springboot.banking_api.dto.TransactionDto;
import com.springboot.banking_api.entity.Transaction;

public interface TransactionService {

    public void saveTransaction(TransactionDto transactionDto);
}
