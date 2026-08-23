package com.standardchartered.jpademo.service;

import com.standardchartered.jpademo.entity.BankAccountJpaEntity;
import com.standardchartered.jpademo.entity.BankCustomerJpaEntity;
import com.standardchartered.jpademo.repository.BankAccountJpaRepository;
import com.standardchartered.jpademo.repository.BankCustomerJpaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/*
 * SYNTAX COMMENTARY: Service Layer with Spring Data JPA Persistence Operations
 */
@Service
public class BankingJpaService {

    @Autowired
    private BankCustomerJpaRepository customerRepository;

    @Autowired
    private BankAccountJpaRepository accountRepository;

    @PostConstruct
    public void initData() {
        if (customerRepository.count() == 0) {
            BankCustomerJpaEntity cust1 = new BankCustomerJpaEntity(null, "Sandra", "Rogers", "sandra@bank.com", "ACTIVE");
            cust1.addAccount(new BankAccountJpaEntity(null, "ACC101", "SAVINGS", new BigDecimal("100000.00")));
            customerRepository.save(cust1);

            BankCustomerJpaEntity cust2 = new BankCustomerJpaEntity(null, "Steve", "Casey", "steve@bank.com", "ACTIVE");
            cust2.addAccount(new BankAccountJpaEntity(null, "ACC102", "CURRENT", new BigDecimal("300000.00")));
            customerRepository.save(cust2);
        }
    }

    public List<BankCustomerJpaEntity> getAll() {
        return customerRepository.findAll();
    }

    /*
     * SYNTAX COMMENTARY: Database Pagination & Sorting
     *
     * PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending()):
     * - Configures SQL OFFSET and LIMIT clauses behind the scenes.
     * - Returns a Page<BankCustomerJpaEntity> containing total element count, total pages, and elements list.
     */
    public Page<BankCustomerJpaEntity> getPaginatedCustomers(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return customerRepository.findAll(pageable);
    }

    /*
     * SYNTAX COMMENTARY: JPA Persistence Context & Automatic Dirty Checking
     *
     * @Transactional:
     * - Begins an active DB transaction upon method entry and commits upon method exit.
     * - Automatic Dirty Checking: Modifying fields on a MANAGED entity (e.g. customer.setFirstName(...)) automatically triggers an SQL UPDATE upon commit.
     */
    @Transactional
    public BankCustomerJpaEntity updateCustomerEmail(Long customerId, String newEmail) {
        BankCustomerJpaEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer ID " + customerId + " not found"));

        // Entity is in MANAGED state in Persistence Context. Field update automatically syncs to SQL DB!
        customer.setEmail(newEmail);
        return customer;
    }

    /*
     * SYNTAX COMMENTARY: Transactional ACID Fund Transfer
     *
     * @Transactional:
     * - Guarantees Atomicity & Consistency. If crediting target account fails, debiting source account is automatically rolled back!
     */
    @Transactional
    public void transferFunds(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        BankAccountJpaEntity source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        BankAccountJpaEntity target = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (source.getAccountBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds in account " + source.getAccountNumber());
        }

        source.setAccountBalance(source.getAccountBalance().subtract(amount));
        target.setAccountBalance(target.getAccountBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(target);
    }
}
