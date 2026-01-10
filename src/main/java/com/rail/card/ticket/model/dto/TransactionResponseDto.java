package com.rail.card.ticket.model.dto;

import com.rail.card.ticket.model.ServicePayment;
import com.rail.card.ticket.model.Transaction;
import com.rail.card.ticket.model.User;
import com.rail.card.ticket.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private Long transactionId;
    private String transactionType;
    private Double amount;
    private Date createdDate;

    private WalletDto wallet;

    public static TransactionResponseDto toDto(Transaction tx) {

        Wallet wallet = tx.getWallet();
        User user = wallet.getUser();

        return new TransactionResponseDto(
                tx.getTransactionId(),
                tx.getTransactionType(),
                tx.getAmount(),
                tx.getCreatedDate(),
                new WalletDto(
                        wallet.getWalletId(),
                        wallet.getBalance(),
                        user.getEmail()
                )
        );
    }
}