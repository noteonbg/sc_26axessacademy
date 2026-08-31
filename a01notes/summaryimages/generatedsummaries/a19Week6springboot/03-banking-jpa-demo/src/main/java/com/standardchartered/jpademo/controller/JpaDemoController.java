package com.standardchartered.jpademo.controller;

import com.standardchartered.jpademo.dto.BankCustomerDto;
import com.standardchartered.jpademo.dto.TransferRequestDto;
import com.standardchartered.jpademo.service.BankingJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jpa/customers")
public class JpaDemoController {

    @Autowired
    private BankingJpaService jpaService;

    @GetMapping
    public ResponseEntity<List<BankCustomerDto>> getAll() {
        return ResponseEntity.ok(jpaService.getAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<BankCustomerDto>> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(jpaService.getPaginatedCustomers(page, size, sortBy));
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<BankCustomerDto> updateEmail(@PathVariable("id") Long id, @RequestParam("email") String email) {
        return ResponseEntity.ok(jpaService.updateCustomerEmail(id, email));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDto request) {
        jpaService.transferFunds(request.sourceAccountId(), request.targetAccountId(), request.amount());
        return ResponseEntity.ok("Successfully transferred $" + request.amount());
    }
}
