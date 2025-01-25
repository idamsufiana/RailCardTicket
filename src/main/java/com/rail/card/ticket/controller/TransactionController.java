package com.rail.card.ticket.controller;

import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.rail.card.ticket.utils.TokenMapper.getEmail;

@RestController
@RequestMapping("/v1/Transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> getbalance(@RequestHeader("Autorization") String authorization) throws TicketException {
        return ResponseEntity.ok(transactionService.balance(getEmail(authorization)));
    }

    @PostMapping
    public ResponseEntity<?> topUp(@RequestHeader("Autorization") String authorization, @RequestBody Double amount) throws TicketException {
        return ResponseEntity.ok(transactionService.topup(getEmail(authorization), amount));
    }

    @PostMapping
    public ResponseEntity<?> transaction(@RequestHeader("Autorization") String authorization, @RequestBody String serviceCode) throws TicketException {
        return ResponseEntity.ok(transactionService.transaction(authorization, serviceCode));
    }

}
