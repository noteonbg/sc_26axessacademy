package com.standardchartered.securitydemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SecureBankingEndpointsController {

    // Public Endpoint (No Auth Required)
    @GetMapping("/public/info")
    public ResponseEntity<String> publicInfo() {
        return ResponseEntity.ok("Public Info: Welcome to Standard Chartered Open Banking Portal");
    }

    // Customer Endpoint (Requires ROLE_CUSTOMER, ROLE_TELLER, or ROLE_ADMIN)
    @GetMapping("/customer/balance")
    public ResponseEntity<String> customerBalance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Authenticated Customer: " + auth.getName() + " | Authorities: " + auth.getAuthorities() + " | Balance: $45,000.00");
    }

    // Teller Endpoint (Requires ROLE_TELLER or ROLE_ADMIN)
    @GetMapping("/teller/daily-summary")
    public ResponseEntity<String> tellerSummary() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Authenticated Teller: " + auth.getName() + " | Daily Branch Total Cleared: $1,250,000.00");
    }

    // Admin Endpoint (Requires ROLE_ADMIN only)
    @GetMapping("/admin/audit-logs")
    public ResponseEntity<String> adminAuditLogs() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Authenticated Admin: " + auth.getName() + " | System Audit Log: 0 Security Violations Detected.");
    }
}
