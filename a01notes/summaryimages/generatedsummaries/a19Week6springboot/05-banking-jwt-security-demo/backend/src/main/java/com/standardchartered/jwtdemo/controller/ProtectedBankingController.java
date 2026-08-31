package com.standardchartered.jwtdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller containing protected endpoints requiring JWT Bearer token authentication.
 */
@RestController
@RequestMapping("/api/v1")
public class ProtectedBankingController {

    // Customer Account Profile (Requires Valid JWT Bearer Token)
    @GetMapping("/account/profile")
    public ResponseEntity<String> getAccountProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("JWT Authenticated User: " + auth.getName() +
                " | Roles: " + auth.getAuthorities() +
                " | Status: Account Active | Tier: Platinum Preferred");
    }

    // Customer Recent Transactions (Requires Valid JWT Bearer Token)
    @GetMapping("/account/transactions")
    public ResponseEntity<String> getAccountTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("JWT Authenticated User: " + auth.getName() +
                " | Recent Transactions: [+$2,500.00 Salary, -$120.50 Groceries, -$45.00 Fuel]");
    }

    // System Admin Status (Requires Valid JWT Bearer Token with ROLE_ADMIN)
    @GetMapping("/admin/system-status")
    public ResponseEntity<String> getSystemStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("JWT Admin User: " + auth.getName() +
                " | System Status: All 12 Microservices Operational | Active JWT Tokens: 1,450");
    }
}
