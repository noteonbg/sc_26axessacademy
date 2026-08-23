package com.standardchartered.securitydemo.service;

import com.standardchartered.securitydemo.entity.SecurityUserEntity;
import com.standardchartered.securitydemo.repository.SecurityUserRepository;
import com.standardchartered.securitydemo.security.BankingUserDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * SYNTAX COMMENTARY: Implementing UserDetailsService for Database Security
 *
 * UserDetailsService:
 * - Single-method interface: loadUserByUsername(String username)
 * - Invoked by DaoAuthenticationProvider during HTTP login attempts to retrieve user identity from database.
 */
@Service
public class BankingUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Seed database with BCrypt-hashed credentials on startup
    @PostConstruct
    public void seedUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(new SecurityUserEntity(null, "customer_alice", passwordEncoder.encode("Pass123!"), "ROLE_CUSTOMER", true));
            userRepository.save(new SecurityUserEntity(null, "teller_bob", passwordEncoder.encode("TellerPass2026!"), "ROLE_TELLER", true));
            userRepository.save(new SecurityUserEntity(null, "admin_carol", passwordEncoder.encode("AdminPass2026!"), "ROLE_ADMIN", true));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found in database"));

        return new BankingUserDetails(user);
    }
}
