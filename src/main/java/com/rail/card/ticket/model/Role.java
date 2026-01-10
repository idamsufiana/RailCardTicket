package com.rail.card.ticket.model;

import com.rail.card.ticket.constant.ApplicationEnum;
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
    private Long roleId;
    @Column(name = "role_name")
    private ApplicationEnum.Group roleName;
    private String description;

}
