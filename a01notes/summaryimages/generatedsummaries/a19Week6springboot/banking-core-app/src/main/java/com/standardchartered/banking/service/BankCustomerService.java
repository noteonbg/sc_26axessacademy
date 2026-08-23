package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.AccountEntity;
import com.standardchartered.banking.entity.CustomerEntity;
import com.standardchartered.banking.exception.CustomerNotFoundException;
import com.standardchartered.banking.repository.AccountRepository;
import com.standardchartered.banking.repository.CustomerRepository;
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
public class BankCustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    // Seed initial demo data on startup
    @PostConstruct
    public void seedInitialData() {
        if (customerRepository.count() == 0) {
            CustomerEntity cust1 = new CustomerEntity(null, "Sandra", "Rogers", "sandra.rogers@bank.com", "555-0101", "ACTIVE");
            AccountEntity acc1 = new AccountEntity(null, "ACC101-SAVINGS", "SAVINGS", "Main Branch", new BigDecimal("100000.00"));
            cust1.addAccount(acc1);
            customerRepository.save(cust1);

            CustomerEntity cust2 = new CustomerEntity(null, "Steve", "Casey", "steve.casey@bank.com", "555-0102", "ACTIVE");
            AccountEntity acc2 = new AccountEntity(null, "ACC102-CURRENT", "CURRENT", "Downtown Branch", new BigDecimal("300000.00"));
            cust2.addAccount(acc2);
            customerRepository.save(cust2);
        }
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Page<CustomerEntity> getCustomersPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return customerRepository.findAll(pageable);
    }

    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Record with ID " + id + " not found"));
    }

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public CustomerEntity updateCustomer(Long id, CustomerEntity updatedCustomer) {
        CustomerEntity existing = getCustomerById(id);
        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setStatus(updatedCustomer.getStatus());
        return existing; // JPA dirty checking syncs updates to DB upon commit
    }

    @Transactional
    public void deleteCustomer(Long id) {
        CustomerEntity existing = getCustomerById(id);
        customerRepository.delete(existing);
    }

    public List<AccountEntity> getAccountsByCustomerId(Long customerId) {
        getCustomerById(customerId); // Verifies customer exists
        return accountRepository.findByCustomerId(customerId);
    }
}
