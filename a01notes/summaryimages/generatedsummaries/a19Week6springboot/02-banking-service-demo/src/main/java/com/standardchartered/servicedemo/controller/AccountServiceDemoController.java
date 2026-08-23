package com.standardchartered.servicedemo.controller;

import com.standardchartered.servicedemo.model.BankAccount;
import com.standardchartered.servicedemo.service.BankingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/services/accounts")
public class AccountServiceDemoController {

    // Field Injection example for Controller delegating to Service layer
    @Autowired
    private BankingAccountService accountService;

    @GetMapping("/{accNo}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable("accNo") String accNo) {
        return ResponseEntity.ok(accountService.getAccountDetails(accNo));
    }

    @PostMapping("/{accNo}/apply-interest")
    public ResponseEntity<String> applyInterest(@PathVariable("accNo") String accNo) {
        BigDecimal interestPaid = accountService.applyQuarterlyInterest(accNo);
        return ResponseEntity.ok("Applied quarterly interest of $" + interestPaid + " to account " + accNo);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") BigDecimal amount) {
        String receipt = accountService.transferMoney(from, to, amount);
        return ResponseEntity.ok(receipt);
    }
}
