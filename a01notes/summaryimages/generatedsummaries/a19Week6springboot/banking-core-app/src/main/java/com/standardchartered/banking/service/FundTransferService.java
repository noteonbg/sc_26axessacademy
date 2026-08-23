package com.standardchartered.banking.service;

import com.standardchartered.banking.dto.TransferRequestDTO;
import com.standardchartered.banking.entity.AccountEntity;
import com.standardchartered.banking.exception.InsufficientBalanceException;
import com.standardchartered.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FundTransferService {

    @Autowired
    private AccountRepository accountRepository;

    // Guaranteed ACID Transaction: Atomicity ensures if credit fails, debit is rolled back automatically
    @Transactional
    public String executeTransfer(TransferRequestDTO request) {
        AccountEntity source = accountRepository.findById(request.getSourceAccountId())
                .orElseThrow(() -> new RuntimeException("Source Account ID " + request.getSourceAccountId() + " not found"));
        AccountEntity target = accountRepository.findById(request.getTargetAccountId())
                .orElseThrow(() -> new RuntimeException("Target Account ID " + request.getTargetAccountId() + " not found"));

        BigDecimal transferAmount = request.getAmount();

        if (source.getAccountBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("Account " + source.getAccountNumber() 
                    + " has insufficient balance (" + source.getAccountBalance() + ") for requested transfer of $" + transferAmount);
        }

        source.setAccountBalance(source.getAccountBalance().subtract(transferAmount));
        target.setAccountBalance(target.getAccountBalance().add(transferAmount));

        accountRepository.save(source);
        accountRepository.save(target);

        return "Successfully transferred $" + transferAmount + " from " + source.getAccountNumber() + " to " + target.getAccountNumber();
    }
}
