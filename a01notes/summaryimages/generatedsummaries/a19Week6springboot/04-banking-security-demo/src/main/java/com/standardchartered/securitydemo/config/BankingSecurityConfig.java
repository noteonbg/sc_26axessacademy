package com.standardchartered.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * SYNTAX COMMENTARY: Spring Security Configuration Architecture
 *
 * @Configuration:
 * - Marks this class as a Spring Configuration Bean factory.
 *
 * @EnableWebSecurity:
 * - Enables Spring Security's web security support and registers the SecurityFilterChain in the Servlet filter pipeline.
 */
@Configuration
@EnableWebSecurity
public class BankingSecurityConfig {

    /*
     * SYNTAX COMMENTARY: Password Encoder Bean
     *
     * BCryptPasswordEncoder:
     * - Implementation of PasswordEncoder that uses the strong BCrypt key-derivation hashing algorithm with random salting.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * SYNTAX COMMENTARY: Security Filter Chain Definition (Spring Security 6+ Lambda DSL Syntax)
     *
     * SecurityFilterChain:
     * - Defines URL authorization rules, CSRF settings, session management, and authentication methods.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF for REST APIs and H2 console frame access
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                
                /*
                 * SYNTAX COMMENTARY: URL Path Authorization Rules
                 *
                 * requestMatchers("/api/v1/public/**").permitAll():
                 * - Allows anonymous, unauthenticated access to public paths.
                 *
                 * requestMatchers("/api/v1/admin/**").hasRole("ADMIN"):
                 * - Restricts access to users having GrantedAuthority "ROLE_ADMIN".
                 *
                 * requestMatchers("/api/v1/teller/**").hasAnyRole("TELLER", "ADMIN"):
                 * - Allows access to users having either "ROLE_TELLER" or "ROLE_ADMIN".
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/api/v1/public/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/teller/**").hasAnyRole("TELLER", "ADMIN")
                        .requestMatchers("/api/v1/customer/**").hasAnyRole("CUSTOMER", "TELLER", "ADMIN")
                        .anyRequest().authenticated()
                )
                
                // Enables HTTP Basic Authentication header processing
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
