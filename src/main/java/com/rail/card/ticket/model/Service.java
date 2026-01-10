package com.rail.card.ticket.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    private String serviceCode;
    private String serviceName;
    @ManyToOne
    @JoinColumn(name="service_icon")
    private Banner banner;
    private Double servicetarif;
}
