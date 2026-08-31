package com.standardchartered.jpademo.service;

import com.standardchartered.jpademo.dto.BankAccountDto;
import com.standardchartered.jpademo.dto.BankCustomerDto;
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

    // Entity -> DTO Mappers
    public BankAccountDto mapAccountToDto(BankAccountJpaEntity account) {
        if (account == null) return null;
        return new BankAccountDto(
            account.getId(),
            account.getAccountNumber(),
            account.getAccountType(),
            account.getAccountBalance()
        );
    }

    public BankCustomerDto mapCustomerToDto(BankCustomerJpaEntity customer) {
        if (customer == null) return null;
        List<BankAccountDto> accountDtos = customer.getAccounts() != null ?
            customer.getAccounts().stream().map(this::mapAccountToDto).toList() : List.of();

        return new BankCustomerDto(
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getStatus(),
            accountDtos
        );
    }

    @Transactional(readOnly = true)
    public List<BankCustomerDto> getAll() {
        return customerRepository.findAll().stream()
                .map(this::mapCustomerToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<BankCustomerDto> getPaginatedCustomers(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<BankCustomerJpaEntity> entityPage = customerRepository.findAll(pageable);
        return entityPage.map(this::mapCustomerToDto);
    }

    @Transactional
    public BankCustomerDto updateCustomerEmail(Long customerId, String newEmail) {
        BankCustomerJpaEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer ID " + customerId + " not found"));

        customer.setEmail(newEmail);
        return mapCustomerToDto(customer);
    }

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
