package com.standardchartered.jpademo.controller;

import com.standardchartered.jpademo.entity.BankCustomerJpaEntity;
import com.standardchartered.jpademo.service.BankingJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jpa/customers")
public class JpaDemoController {

    @Autowired
    private BankingJpaService jpaService;

    @GetMapping
    public List<BankCustomerJpaEntity> getAll() {
        return jpaService.getAll();
    }

    @GetMapping("/paged")
    public Page<BankCustomerJpaEntity> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return jpaService.getPaginatedCustomers(page, size, sortBy);
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<BankCustomerJpaEntity> updateEmail(@PathVariable("id") Long id, @RequestParam("email") String email) {
        return ResponseEntity.ok(jpaService.updateCustomerEmail(id, email));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam("sourceId") Long sourceId,
            @RequestParam("targetId") Long targetId,
            @RequestParam("amount") BigDecimal amount) {
        jpaService.transferFunds(sourceId, targetId, amount);
        return ResponseEntity.ok("Successfully transferred $" + amount);
    }
}
