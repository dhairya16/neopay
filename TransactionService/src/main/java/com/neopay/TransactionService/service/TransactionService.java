package com.neopay.TransactionService.service;

import com.neopay.TransactionService.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    List<Transaction> getTransactions();
}
