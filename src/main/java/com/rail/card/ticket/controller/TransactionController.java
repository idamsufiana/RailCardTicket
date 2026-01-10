package com.rail.card.ticket.controller;

import com.rail.card.ticket.config.Secured;
import com.rail.card.ticket.constant.ApplicationEnum;
import com.rail.card.ticket.model.dto.HistoryDto;
import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.model.dto.TopUpRequest;
import com.rail.card.ticket.model.dto.TransactionRequest;
import com.rail.card.ticket.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.rail.card.ticket.utils.TokenUtils.getEmail;

@RestController
@RequestMapping("/v1/Transaction")
public class TransactionController extends BaseController {

    @Autowired
    TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getbalance")
    public ResponseEntity<?> getbalance() throws TicketException {
        return success(transactionService.balance(getEmail()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/topUp")
    public ResponseEntity<?> topUp(@RequestBody TopUpRequest amount) throws TicketException {
        return success(transactionService.topup(getEmail(), amount.getAmount()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestBody TransactionRequest serviceCode) throws TicketException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return success(transactionService.transaction(getEmail(), serviceCode.getService_code()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestParam(required = false,defaultValue = "0") int page,
                                     @RequestParam(required = false,defaultValue = "100") int limit,
                                     @RequestParam(required = false) String sort,
                                     @RequestParam(required = false,defaultValue = "true") boolean desc) throws TicketException {
        Pageable pageable = this.pageFromRequest(page, limit, sort, desc);
        return success(transactionService.findAllAsDto(pageable));
    }

}
