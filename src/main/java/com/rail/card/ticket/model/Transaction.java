package com.rail.card.ticket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction extends CrudEntity{
    private String serviceCode;
    private String transactionType;
    private Double amount;
    @ManyToOne
    private Wallet wallet;
}
