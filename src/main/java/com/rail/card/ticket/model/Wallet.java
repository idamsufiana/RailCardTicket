package com.rail.card.ticket.model;

import com.rail.card.ticket.constant.ApplicationEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "wallet")
public class Wallet extends CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    private Double balance;
    private ApplicationEnum.Status status;

    @OneToOne
    private User user;

}
