package com.rail.card.ticket.model;

import com.rail.card.ticket.constant.ApplicationEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "wallet")
public class Wallet extends CrudEntity{
    private Double balance;
    private ApplicationEnum.Status status;

    @ManyToOne
    private User user;

}
