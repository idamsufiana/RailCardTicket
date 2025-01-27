package com.rail.card.ticket.utils;


import org.springframework.security.core.context.SecurityContextHolder;

public class TokenUtils {

    public static String getEmail(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return email;
    }


}
