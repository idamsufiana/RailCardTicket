package com.rail.card.ticket.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String Authtoken;
    public LoginResponse(String Authtoken) {
        this.Authtoken = Authtoken;
    }
}
