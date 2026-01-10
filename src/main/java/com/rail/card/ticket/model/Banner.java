package com.rail.card.ticket.model;

import jakarta.persistence.*;


@Entity
@Table(name ="banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long bannerId;
    private String bannerImage;
    private String description;
}
