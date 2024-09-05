package com.springboot.banking_api.repository;

import com.springboot.banking_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, String > {
}
