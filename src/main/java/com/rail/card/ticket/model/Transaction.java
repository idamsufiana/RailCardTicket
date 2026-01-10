package com.rail.card.ticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction extends CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String status;
    private Double amount;
    private String referenceNo;


    @JoinColumn(name="wallet_id")
    @ManyToOne
    private Wallet wallet;

}
