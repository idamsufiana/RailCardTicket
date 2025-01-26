package com.rail.card.ticket.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class CrudEntity implements Serializable {
    protected Date createdDate;
    protected Date updatedDate;
    protected String createdBy;
    protected String updatedBy;

}
