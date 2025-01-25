package com.rail.card.ticket.utils;

import com.google.gson.Gson;
import com.rail.card.ticket.exception.TicketException;
import org.apache.commons.codec.binary.Base64;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class TokenMapper {

    public static Date expToken (String token) throws TicketException {
        String expired = "Token expired";
        String[] splitString = token.split("\\.");
        String base64EncodedBody = splitString[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        Map<String, String> bodyToken = new Gson().fromJson(body, Map.class);
        DecimalFormat format = new DecimalFormat("###");
        format.setMinimumIntegerDigits(0);
        format.setMaximumFractionDigits(2000);
        String expString = format.format(bodyToken.get("exp"));
        Date exp = Date.from(Instant.ofEpochSecond(Long.parseLong(expString)));
        if (exp.getTime() < Calendar.getInstance().getTimeInMillis()) {
            throw new TicketException(404, expired);
        }
        return exp;
    }

    public static String getEmail (String token) throws TicketException {
        String expired = "Token expired";
        String[] splitString = token.split("\\.");
        String base64EncodedBody = splitString[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        Map<String, String> bodyToken = new Gson().fromJson(body, Map.class);
        String email = bodyToken.get("email");
        return email;
    }

}
