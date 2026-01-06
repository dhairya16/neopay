package com.neopay.TransactionService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neopay.TransactionService.entity.Transaction;
import com.neopay.TransactionService.repository.TransactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.neopay.core.dto.TransactionInitiatedEvent;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;

    KafkaTemplate<String, TransactionInitiatedEvent> kafkaTemplate;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ObjectMapper objectMapper, KafkaTemplate<String, TransactionInitiatedEvent> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) throws Exception {
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionInitiatedEvent transactionInitiatedEvent =
                TransactionInitiatedEvent.builder()
                        .transactionId(transaction.getId())
                        .senderId(transaction.getSenderId())
                        .receiverId(transaction.getReceiverId())
                        .amount(transaction.getAmount())
                        .status(transaction.getStatus())
                        .build();

        SendResult<String, TransactionInitiatedEvent> result =
                kafkaTemplate.send("transaction-initiated", savedTransaction.getId().toString(), transactionInitiatedEvent).get();

        return savedTransaction;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
