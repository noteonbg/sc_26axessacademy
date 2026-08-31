package com.bank.account.service;

import com.bank.account.model.Account;

/**
 * Business Service Interface for Account Management.
 */
public interface AccountService {
    Account getAccountDetails(String accountId);
    Account createAccount(String accountId, String holderName, double initialDeposit, String type);
    Account deposit(String accountId, double amount);
    Account withdraw(String accountId, double amount);
    boolean isHighValueCustomer(String accountId);
}
