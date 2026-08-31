package com.bank.account.repository;

import com.bank.account.model.Account;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Account data access operations.
 */
public interface AccountRepository {
    Optional<Account> findById(String accountId);
    Account save(Account account);
    List<Account> findAll();
    boolean deleteById(String accountId);
}
