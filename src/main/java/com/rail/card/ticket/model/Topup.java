package com.rail.card.ticket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "topup")
public class Topup extends CrudEntity{
    private Double amount;
    private String topupMethod;
    private Date topupDate;
    @OneToOne
    private Transaction transaction;
}
