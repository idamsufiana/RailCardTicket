package com.rail.card.ticket.controller;

import com.rail.card.ticket.config.Secured;
import com.rail.card.ticket.constant.ApplicationEnum;
import com.rail.card.ticket.model.dto.HistoryDto;
import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.rail.card.ticket.utils.TokenUtils.getEmail;

@RestController
@RequestMapping("/v1/Transaction")
public class TransactionController extends BaseController {

    @Autowired
    TransactionService transactionService;

    @Secured({ApplicationEnum.Group.Admin})
    @PostMapping("/getbalance")
    public ResponseEntity<?> getbalance() throws TicketException {
        return success(transactionService.balance(getEmail()));
    }

    @Secured({ApplicationEnum.Group.Admin})
    @PostMapping("/topUp")
    public ResponseEntity<?> topUp(@RequestBody Double amount) throws TicketException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return success(transactionService.topup(getEmail(), amount));
    }

    @Secured({ApplicationEnum.Group.Admin})
    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestBody String serviceCode) throws TicketException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return success(transactionService.transaction(getEmail(), serviceCode));
    }

    @Secured({ApplicationEnum.Group.Admin})
    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestParam(required = false,defaultValue = "0") int page,
                                     @RequestParam(required = false,defaultValue = "100") int limit,
                                     @RequestParam(required = false) String sort,
                                     @RequestParam(required = false,defaultValue = "true") boolean asc,
                                     HistoryDto dto) throws TicketException {
        Pageable pageable = this.pageFromRequest(page, limit, sort, asc);
        return success(transactionService.findAllAsDto(dto, pageable));
    }

}
