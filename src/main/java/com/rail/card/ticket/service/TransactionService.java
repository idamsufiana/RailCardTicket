package com.rail.card.ticket.service;

import com.rail.card.ticket.support.GeneratorSequence;
import com.rail.card.ticket.model.dto.HistoryDto;
import com.rail.card.ticket.model.dto.ResponseTransaction;
import com.rail.card.ticket.model.dto.TransactionDto;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;

import static java.time.LocalDate.now;


@org.springframework.stereotype.Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    GeneratorSequence generatorSequence;

    public Double balance(String email) throws TicketException {
        Wallet wallet = new Wallet();
        try{
            wallet = walletRepository.findByEmail(email);
        }catch (Exception e){
            throw new TicketException(e.getMessage());
        }
        return wallet.getBalance();
    }

    public Wallet topup(String email, Double amount) throws TicketException {
        Wallet wallet = new Wallet();
        try{
            wallet = walletRepository.findByEmail(email);
            Double result = wallet.getBalance()+amount;
            validateTopUp(wallet, amount);
            wallet.setBalance(result);
            walletRepository.save(wallet);
            TransactionDto dto = new TransactionDto();
            dto.setAmount(amount);
            dto.setTransactionType("TOPUP");
            dto.setWallet(wallet);
            saveToTransaction(dto);
        }catch (Exception e){
            throw new TicketException(e.getMessage());
        }
        return null;
    }

    @Transactional
    public ResponseTransaction transaction(String email, String serviceCode) throws TicketException {
        ResponseTransaction responseTransaction = new ResponseTransaction();
        try{
            Service service = serviceRepository.findFirstByServiceCode(serviceCode);
            if(service == null){
                throw new TicketException("service Code is not available");
            }
            Wallet wallet = walletRepository.findByEmail(email);
            if(wallet == null){
                throw new TicketException("No data found");
            }
            if(wallet.getBalance() < service.getServiceTarif()){
                throw new Exception("Insufficient Fund");
            }
            // get Amount
            wallet.setBalance(wallet.getBalance() - service.getServiceTarif());
            walletRepository.save(wallet);
            responseTransaction.setAmount(service.getServiceTarif());
            responseTransaction.setServiceCode(service.getServiceCode());
            responseTransaction.setServiceName(service.getServiceName());
            responseTransaction.setInvoiceCode(setRequestId());

            Transaction transaction = new Transaction();
            transaction.setWallet(wallet);
            transaction.setAmount(service.getServiceTarif());
            transaction.setTransactionType("PAYMENT");
            transaction.setCreatedDate(new Date());
            transactionRepository.save(transaction);
            TransactionDto dto = new TransactionDto();
            dto.setAmount(service.getServiceTarif());
            dto.setWallet(wallet);
            dto.setTransactionType("PAYMENT");
            saveToTransaction(dto);
        }catch (Exception e){
            throw new TicketException(e.getMessage());
        }
        return responseTransaction;
    }

    private String setRequestId() {
        return "INV" + String.format("%010d", generatorSequence.get("rail_seq"));
    }

    public Page<Transaction> findAllAsDto(Pageable pageable) throws TicketException {
        return transactionRepository.findAll(pageable);
    }

    public void validateAmount(Wallet wallet, Double amount) throws TicketException {
        if(wallet== null){
            throw new TicketException("Data not found");
        }
        if(wallet.getBalance() < amount){
            throw new TicketException("Insufficient Fund");
        }
    }

    public void validateTopUp(Wallet wallet, Double amount) throws TicketException {
        if(wallet== null){
            throw new TicketException("Data not found");
        }
    }

    public void saveToTransaction(TransactionDto dto) throws TicketException {
      Transaction transaction = new Transaction();
      transaction.setAmount(dto.getAmount());
    }
}
