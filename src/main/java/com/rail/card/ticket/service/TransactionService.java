package com.rail.card.ticket.service;

import com.rail.card.ticket.config.GeneratorSequence;
import com.rail.card.ticket.dto.HistoryDto;
import com.rail.card.ticket.dto.ResponseTransaction;
import com.rail.card.ticket.dto.TransactionDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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
            validateAmount(wallet, amount);
            wallet.setBalance(wallet.getBalance()+amount);
            walletRepository.save(wallet);
            TransactionDto dto = new TransactionDto();
            dto.setAmount(amount);
            dto.setTransactionType("TOPUP");
            dto.setWallet(wallet);
            saveToTransaction(dto);
        }catch (Exception e){
            throw new TicketException(e.getMessage());
        }
        return wallet;
    }

    @Transactional
    public ResponseTransaction transaction(String Autorization, String serviceCode) throws TicketException {
        ResponseTransaction responseTransaction = new ResponseTransaction();
        try{
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
            responseTransaction.setInvoiceCode(setRequestId());

            TransactionDto dto = new TransactionDto();
            dto.setAmount(servicePayment.getAmount());
            dto.setWallet(wallet);
            saveToTransaction(dto);
        }catch (Exception e){
            throw new TicketException(e.getMessage());
        }
        return responseTransaction;
    }

    private String setRequestId() {
        return "INV" + String.format("%010d", generatorSequence.get("rail_seq"));
    }

    public Page<Transaction> findAllAsDto(String Autorization, HistoryDto date, Pageable pageable) throws TicketException {
        expToken(Autorization);
        List<Transaction> list = transactionRepository.findByDateRange(date.getDateFrom(), date.getDateTo());
        return new PageImpl<>(list, pageable, list.size());
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

    public void saveToTransaction(TransactionDto dto) throws TicketException {
      Transaction transaction = new Transaction();
      transaction.setAmount(dto.getAmount());
      if(!transaction.getTransactionType().equals("TOPUP")){
          transaction.setTransactionType("PAYMENT");
      }
    }
}
