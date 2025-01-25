package com.rail.card.ticket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTransaction {
    private String invoiceCode;
    private String serviceCode;
    private String serviceName;
    private Double amount;
    private String paymentType;
}
