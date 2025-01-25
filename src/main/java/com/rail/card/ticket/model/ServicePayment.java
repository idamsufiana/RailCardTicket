package com.rail.card.ticket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "service_payment")
public class ServicePayment extends CrudEntity{
    private String serviceCode;
    private String serviceName;
    private Double amount;
}
