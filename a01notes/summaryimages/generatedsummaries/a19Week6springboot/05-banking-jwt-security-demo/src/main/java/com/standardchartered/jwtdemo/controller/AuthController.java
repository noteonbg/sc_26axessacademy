package com.standardchartered.jwtdemo.controller;

import com.standardchartered.jwtdemo.dto.JwtResponse;
import com.standardchartered.jwtdemo.dto.LoginRequest;
import com.standardchartered.jwtdemo.dto.RegisterRequest;
import com.standardchartered.jwtdemo.entity.JwtUserEntity;
import com.standardchartered.jwtdemo.repository.JwtUserRepository;
import com.standardchartered.jwtdemo.service.JwtService;
import com.standardchartered.jwtdemo.service.JwtUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtUserDetailsService userDetailsService;
    private final JwtUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          JwtUserDetailsService userDetailsService,
                          JwtUserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. LOGIN & GENERATE JWT TOKEN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate credentials via AuthenticationManager
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Load UserDetails and generate JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String jwtToken = jwtService.generateToken(userDetails);

        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getUsername(), role));
    }

    // 2. REGISTER NEW USER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username is already taken!");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        String role = registerRequest.getRole() != null && !registerRequest.getRole().isBlank()
                ? registerRequest.getRole()
                : "ROLE_CUSTOMER";

        JwtUserEntity newUser = new JwtUserEntity(null, registerRequest.getUsername(), encodedPassword, role, true);
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully! You can now login.");
    }
}
