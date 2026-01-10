package com.rail.card.ticket.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenUtils {

    public static String getEmail(){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();
        return email;
    }


}
