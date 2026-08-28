package com.example.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Demo Controller", description = "Endpoints demonstrating Basic Spring Security role-based access")
public class DemoController {

    /**
     * Endpoint f1: Accessible ONLY by ADMIN role (User A).
     */
    @GetMapping("/f1")
    @Operation(
        summary = "Function f1 (ADMIN only)",
        description = "Requires HTTP Basic Auth with ADMIN role (User A). Returns 200 OK and 'f1 at work'.",
        security = @SecurityRequirement(name = "basicAuth")
    )
    public ResponseEntity<String> f1() {
        return ResponseEntity.ok("f1 at work");
    }

    /**
     * Endpoint f2: Accessible ONLY by USER role (Normal User).
     */
    @GetMapping("/f2")
    @Operation(
        summary = "Function f2 (USER only)",
        description = "Requires HTTP Basic Auth with USER role (Normal User). Returns 200 OK and 'f2 at work'.",
        security = @SecurityRequirement(name = "basicAuth")
    )
    public ResponseEntity<String> f2() {
        return ResponseEntity.ok("f2 at work");
    }

    /**
     * Endpoint f3: Accessible by ANYONE (Public / Unauthenticated).
     */
    @GetMapping("/f3")
    @Operation(
        summary = "Function f3 (Public)",
        description = "Publicly accessible by anyone without authentication. Returns 200 OK and 'f3 at work'."
    )
    public ResponseEntity<String> f3() {
        return ResponseEntity.ok("f3 at work");
    }
}
