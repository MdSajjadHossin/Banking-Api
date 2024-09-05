package com.springboot.banking_api.service;

import com.springboot.banking_api.dto.TransactionDto;
import com.springboot.banking_api.entity.Transaction;
import com.springboot.banking_api.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {

        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESSFUL")
                .build();
        transactionRepo.save(transaction);
        System.out.println("Transaction saved successful");

    }
}
