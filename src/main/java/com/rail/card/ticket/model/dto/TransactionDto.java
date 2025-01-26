package com.rail.card.ticket.model.dto;

import com.rail.card.ticket.model.ServicePayment;
import com.rail.card.ticket.model.Wallet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TransactionDto {
    private Double amount;
    private String transactionType;
    private Wallet wallet;
    private ServicePayment servicePayment;
}
