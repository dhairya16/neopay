package com.neopay.core.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInitiatedEvent {
    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private Double amount;
    private String status;

    @Override
    public String toString() {
        return "TransactionInitiatedEvent{" +
                "transactionId=" + transactionId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}

