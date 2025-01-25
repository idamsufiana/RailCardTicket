package com.rail.card.ticket.controller;

import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> getbalance(@RequestHeader("Autorization") String authorization){
        String email = "";
        transactionService.balance(email);
        return ResponseEntity.ok("");
    }

    @PostMapping
    public ResponseEntity<?> topUp(@RequestHeader("Autorization") String authorization, @RequestBody Double amount) throws TicketException {
        String email = "";
        transactionService.topup(email, amount);
        return ResponseEntity.ok("");
    }

    @PostMapping
    public ResponseEntity<?> transaction(@RequestHeader("Autorization") String authorization, @RequestBody String serviceCode) throws TicketException {
        transactionService.transaction(authorization, serviceCode);
        return ResponseEntity.ok("");
    }

}
