package com.rail.card.ticket.repository;

import com.rail.card.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUserName(String name);
}
