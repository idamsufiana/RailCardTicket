package com.rail.card.ticket.repository;

import com.rail.card.ticket.constant.ApplicationEnum;
import com.rail.card.ticket.model.Role;
import com.rail.card.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ApplicationEnum.Group name);
}
