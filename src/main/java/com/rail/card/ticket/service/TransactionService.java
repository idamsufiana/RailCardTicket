package com.rail.card.ticket.service;

import com.rail.card.ticket.dto.ResponseTransaction;
import com.rail.card.ticket.exception.TicketException;
import com.rail.card.ticket.model.ServicePayment;
import com.rail.card.ticket.model.Transaction;
import com.rail.card.ticket.model.User;
import com.rail.card.ticket.model.Wallet;
import com.rail.card.ticket.repository.ServiceRepository;
import com.rail.card.ticket.repository.TransactionRepository;
import com.rail.card.ticket.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.rail.card.ticket.utils.TokenMapper.expToken;
import static com.rail.card.ticket.utils.TokenMapper.getEmail;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    WalletRepository walletRepository;

    public Double balance(String email){
        Wallet wallet = walletRepository.findByEmail(email);
        return wallet.getBalance();
    }
    public Wallet topup(String email, Double amount) throws TicketException {
        Wallet wallet = walletRepository.findByEmail(email);
        validateAmount(wallet, amount);
        wallet.setBalance(wallet.getBalance()+amount);
        walletRepository.save(wallet);
        return wallet;
    }

    @Transactional
    public ResponseTransaction transaction(String Autorization, String serviceCode) throws TicketException {
        ResponseTransaction responseTransaction = new ResponseTransaction();
        validate(Autorization, serviceCode);
        // get Amount
        ServicePayment servicePayment = serviceRepository.findFirstByServiceCode(serviceCode);
        String email = getEmail(Autorization);
        Wallet wallet = walletRepository.findByEmail(email);
        validateAmount(wallet, servicePayment.getAmount());
        wallet.setBalance(wallet.getBalance() - servicePayment.getAmount());
        walletRepository.save(wallet);
        responseTransaction.setAmount(servicePayment.getAmount());
        responseTransaction.setServiceCode(servicePayment.getServiceCode());
        responseTransaction.setServiceName(servicePayment.getServiceName());
        return responseTransaction;
    }
    public List<Transaction> history(LocalDate dateFrom , LocalDate dateTo){
        return transactionRepository.findAll();
    }

    public void validate(String Autorization, String serviceCode) throws TicketException {
        expToken(Autorization);
        if(serviceRepository.findFirstByServiceCode(serviceCode) == null){
            throw new TicketException("service Code is not available");
        }
    }

    public void validateAmount(Wallet wallet, Double amount) throws TicketException {
        if(wallet== null){
            throw new TicketException("Data not found");
        }
        if(wallet.getBalance() < amount){
            throw new TicketException("Insufficient Fund");
        }
    }
}
