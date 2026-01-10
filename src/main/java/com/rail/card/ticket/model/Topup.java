package com.rail.card.ticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "topup")
public class Topup extends CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topupId;
    private String topupMethod;

    @ManyToOne
    private Transaction transaction;
}
