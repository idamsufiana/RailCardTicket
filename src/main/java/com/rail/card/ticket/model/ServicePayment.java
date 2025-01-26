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
    protected Long serviceId;
    private String serviceCode;
    private String serviceName;
    private Double amount;
}
