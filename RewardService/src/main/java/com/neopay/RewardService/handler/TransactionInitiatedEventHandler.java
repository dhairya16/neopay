package com.neopay.RewardService.handler;

import com.neopay.RewardService.entity.Reward;
import com.neopay.RewardService.repository.RewardRepository;
import com.neopay.core.dto.TransactionInitiatedEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "transaction-initiated", groupId = "transaction-initiated-rewards-consumer")
public class TransactionInitiatedEventHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RewardRepository rewardRepository;

    @Value("${reward.percentage}")
    private double rewardPercentage;

    public TransactionInitiatedEventHandler(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    @KafkaHandler
    public void handle(TransactionInitiatedEvent transactionInitiatedEvent) {
        try {
            LOGGER.info("[RewardService] Received a new event " + transactionInitiatedEvent.toString());

            if (rewardRepository.existsByTransactionId(transactionInitiatedEvent.getTransactionId())) {
                LOGGER.error("Reward already exists for transaction " + transactionInitiatedEvent.getTransactionId());
                return;
            }

            double rewardPoints = (rewardPercentage * transactionInitiatedEvent.getAmount()) / (double) 100;

            Reward reward = Reward.builder()
                    .userId(transactionInitiatedEvent.getSenderId())
                    .transactionId(transactionInitiatedEvent.getTransactionId())
                    .points(rewardPoints)
                    .build();

            rewardRepository.save(reward);
        } catch (Exception e) {
            LOGGER.error("[RewardService] Failed to process reward " + e.toString());
            throw new RuntimeException(e);
        }
    }
}