package com.rail.card.ticket.repository;

import com.rail.card.ticket.model.ServicePayment;
import com.rail.card.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServicePayment, Long> {
    ServicePayment findFirstByServiceCode(String serviceCode);
}
