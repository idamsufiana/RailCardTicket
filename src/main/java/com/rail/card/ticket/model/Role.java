package com.rail.card.ticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role extends CrudEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long roleId;
    private String role;
    private String description;
}
