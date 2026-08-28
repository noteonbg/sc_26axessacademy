package com.standardchartered.jwtdemo.service;

import com.standardchartered.jwtdemo.entity.JwtUserEntity;
import com.standardchartered.jwtdemo.repository.JwtUserRepository;
import com.standardchartered.jwtdemo.security.JwtUserDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final JwtUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtUserDetailsService(JwtUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedDefaultUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(new JwtUserEntity(null, "customer_alice", passwordEncoder.encode("Pass123!"), "ROLE_CUSTOMER", true));
            userRepository.save(new JwtUserEntity(null, "admin_carol", passwordEncoder.encode("AdminPass2026!"), "ROLE_ADMIN", true));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new JwtUserDetails(user);
    }
}
