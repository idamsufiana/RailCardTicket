package com.rail.card.ticket.repository;

import com.rail.card.ticket.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
