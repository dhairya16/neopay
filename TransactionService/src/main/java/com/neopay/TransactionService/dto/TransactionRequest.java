package com.neopay.TransactionService.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String senderName;
    private String receiverName;
    private Double amount;
}
