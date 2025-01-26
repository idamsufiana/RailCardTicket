package com.rail.card.ticket.repository;

import com.rail.card.ticket.model.User;
import com.rail.card.ticket.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("select r from Wallet r where r.user.email = ?1 ")
    Wallet findByEmail(String email);


}
