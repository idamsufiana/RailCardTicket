package com.rail.card.ticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "service_payment")
public class ServicePayment extends CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Double priceSnapshot;
    private Double feeSnapshot;
    private Double taxSnapshot;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name="service_id")
    private Service service;

}
