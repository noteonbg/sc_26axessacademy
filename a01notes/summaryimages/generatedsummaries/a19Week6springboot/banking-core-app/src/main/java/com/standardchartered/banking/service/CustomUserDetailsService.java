package com.standardchartered.banking.service;

import com.standardchartered.banking.entity.BankUserEntity;
import com.standardchartered.banking.repository.BankUserRepository;
import com.standardchartered.banking.security.BankUserDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BankUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedInitialUsers() {
        if (userRepository.count() == 0) {
            // Seed Customer, Teller, and Admin users with BCrypt Hashed Passwords
            userRepository.save(new BankUserEntity(null, "sandra.rogers@bank.com", passwordEncoder.encode("Pass123!"), "ROLE_CUSTOMER", true));
            userRepository.save(new BankUserEntity(null, "teller_joe@bank.com", passwordEncoder.encode("TellerPass2026!"), "ROLE_TELLER", true));
            userRepository.save(new BankUserEntity(null, "admin_sarah@bank.com", passwordEncoder.encode("AdminPass2026!"), "ROLE_ADMIN", true));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BankUserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return new BankUserDetails(user);
    }
}
