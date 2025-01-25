package com.rail.card.ticket.repository;

import com.rail.card.ticket.model.Topup;
import com.rail.card.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopUpRepository extends JpaRepository<Topup, Long> {
}
