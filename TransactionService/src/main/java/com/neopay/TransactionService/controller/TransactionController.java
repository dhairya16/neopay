package com.neopay.TransactionService.controller;

import com.neopay.TransactionService.entity.Transaction;
import com.neopay.TransactionService.model.ErrorMessage;
import com.neopay.TransactionService.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions/")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Transaction transaction) {
        Transaction createdTxn = null;
        try {
            createdTxn = transactionService.createTransaction(transaction);
        } catch (Exception e) {
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .timestamp(new Date())
                    .message(e.getMessage())
                    .details("/products")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTxn);
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }
}
