package com.rail.card.ticket.repository;

import com.rail.card.ticket.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT e FROM Transaction e WHERE e.createdDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
