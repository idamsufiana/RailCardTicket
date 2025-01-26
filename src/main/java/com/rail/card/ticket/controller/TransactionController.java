package com.rail.card.ticket.controller;

import com.rail.card.ticket.model.dto.HistoryDto;
import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.rail.card.ticket.utils.TokenMapper.getEmail;

@RestController
@RequestMapping("/v1/Transaction")
public class TransactionController extends BaseController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/getbalance")
    public ResponseEntity<?> getbalance(@RequestHeader("Autorization") String authorization) throws TicketException {
        return success(transactionService.balance(getEmail(authorization)));
    }

    @PostMapping("/topUp")
    public ResponseEntity<?> topUp(@RequestHeader("Autorization") String authorization, @RequestBody Double amount) throws TicketException {
        return success(transactionService.topup(getEmail(authorization), amount));
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestHeader("Autorization") String authorization, @RequestBody String serviceCode) throws TicketException {
        return success(transactionService.transaction(authorization, serviceCode));
    }

    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestHeader("Autorization") String authorization, @RequestParam(required = false,defaultValue = "0") int page,
                                     @RequestParam(required = false,defaultValue = "100") int limit,
                                     @RequestParam(required = false) String sort,
                                     @RequestParam(required = false,defaultValue = "true") boolean asc,
                                     HistoryDto dto) throws TicketException {
        Pageable pageable = this.pageFromRequest(page, limit, sort, asc);
        return success(transactionService.findAllAsDto(authorization, dto, pageable));
    }

}
