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
    protected Long transactionId;
    private String serviceCode;
    private String transactionType;
    private Double amount;
    @ManyToOne
    private Wallet wallet;
    @ManyToOne
    private ServicePayment servicePayment;
}
