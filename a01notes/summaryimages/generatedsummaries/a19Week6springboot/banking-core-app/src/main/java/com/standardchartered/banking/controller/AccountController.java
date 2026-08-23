package com.standardchartered.banking.controller;

import com.standardchartered.banking.entity.AccountEntity;
import com.standardchartered.banking.service.BankCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class AccountController {

    @Autowired
    private BankCustomerService customerService;

    // GET /api/v1/customers/{customerId}/accounts -> Fetch all accounts associated with customer
    @GetMapping("/{customerId}/accounts")
    public List<AccountEntity> getAccountsByCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getAccountsByCustomerId(customerId);
    }
}
