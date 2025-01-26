package com.rail.card.ticket.dto;

import com.rail.card.ticket.model.ServicePayment;
import com.rail.card.ticket.model.Wallet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private Double amount;
    private String transactionType;
    private Wallet wallet;
    private ServicePayment servicePayment;
}
