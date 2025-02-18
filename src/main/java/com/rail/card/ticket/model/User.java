package com.rail.card.ticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends CrudEntity{
    @Id
    private String email;
    private String firstName;
    private String password;
    private String lastName;
    private Boolean status= false;
    @ManyToOne
    private Role role;

}