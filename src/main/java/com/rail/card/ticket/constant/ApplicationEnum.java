package com.rail.card.ticket.constant;

public class ApplicationEnum {
    public enum Group {
        Admin,
        User;

        public String toRole() {
            return "ROLE_" + name().toUpperCase();
        }
    }

    public enum Status {
        ACTIVE, INACTIVE, SUSPENDED
    }
}
