package com.rail.card.ticket.service;

import com.rail.card.ticket.model.dto.TransactionResponseDto;
import com.rail.card.ticket.support.GeneratorSequence;
import com.rail.card.ticket.model.dto.ResponseTransaction;
import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.model.Service;
import com.rail.card.ticket.model.Transaction;
import com.rail.card.ticket.model.Wallet;
import com.rail.card.ticket.repository.ServiceRepository;
import com.rail.card.ticket.repository.TransactionRepository;
import com.rail.card.ticket.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Date;

import static java.time.LocalDate.now;


@org.springframework.stereotype.Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ServiceRepository serviceRepository;
    private final WalletRepository walletRepository;
    private final GeneratorSequence generatorSequence;

    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            ServiceRepository serviceRepository,
            WalletRepository walletRepository,
            GeneratorSequence generatorSequence
    ) {
        this.transactionRepository = transactionRepository;
        this.serviceRepository = serviceRepository;
        this.walletRepository = walletRepository;
        this.generatorSequence = generatorSequence;
    }

    /* ===================== BALANCE ===================== */

    public Double balance(String email) throws TicketException {
        Wallet wallet = findWalletByEmail(email);
        return wallet.getBalance();
    }

    /* ===================== TOP UP ===================== */

    public TransactionResponseDto topup(String email, Double amount) throws TicketException {
        validateAmountPositive(amount);

        Wallet wallet = findWalletByEmail(email);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        return saveTransaction(wallet, amount, "TOPUP");
    }

    /* ===================== PAYMENT ===================== */

    public ResponseTransaction transaction(String email, String serviceCode) throws TicketException {
        Service service = serviceRepository.findFirstByServiceCode(serviceCode);
        if (service == null) {
            throw new TicketException("Service code is not available");
        }

        Wallet wallet = findWalletByEmail(email);
        validateSufficientBalance(wallet, service.getServiceTarif());

        wallet.setBalance(wallet.getBalance() - service.getServiceTarif());
        walletRepository.save(wallet);

        saveTransaction(wallet, service.getServiceTarif(), "PAYMENT");

        return buildResponse(service);
    }

    /* ===================== HISTORY ===================== */

    public Page<Transaction> findAllAsDto(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    /* ===================== PRIVATE HELPERS ===================== */

    private Wallet findWalletByEmail(String email) throws TicketException {
        Wallet wallet = walletRepository.findByEmail(email);
        if (wallet == null) {
            throw new TicketException("Wallet not found");
        }
        return wallet;
    }

    private void validateSufficientBalance(Wallet wallet, Double amount) throws TicketException {
        if (wallet.getBalance() < amount) {
            throw new TicketException("Insufficient Fund");
        }
    }

    private void validateAmountPositive(Double amount) throws TicketException {
        if (amount == null || amount <= 0) {
            throw new TicketException("Amount must be greater than zero");
        }
    }

    private TransactionResponseDto saveTransaction(Wallet wallet, Double amount, String type) {
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(new Date());
        transactionRepository.save(transaction);

        return TransactionResponseDto.toDto(transaction);
    }

    private ResponseTransaction buildResponse(Service service) {
        ResponseTransaction response = new ResponseTransaction();
        response.setAmount(service.getServiceTarif());
        response.setServiceCode(service.getServiceCode());
        response.setServiceName(service.getServiceName());
        response.setInvoiceCode(generateInvoice());
        return response;
    }

    private String generateInvoice() {
        return "INV" + String.format("%010d", generatorSequence.get("rail_seq"));
    }
}
