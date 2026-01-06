package com.neopay.NotificationService.handler;

import com.neopay.core.dto.TransactionInitiatedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "transaction-initiated", groupId = "transaction-initiated-events")
public class TransactionInitiatedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(TransactionInitiatedEvent transactionInitiatedEvent) {
        LOGGER.info("Received a new event " + transactionInitiatedEvent.toString());

    }
}