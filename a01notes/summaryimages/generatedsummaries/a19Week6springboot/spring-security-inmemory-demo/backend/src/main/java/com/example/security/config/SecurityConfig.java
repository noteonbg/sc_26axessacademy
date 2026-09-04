package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @Configuration: Indicates that this class contains @Bean definition methods
 *                 that Spring container processes to generate Spring Beans.
 * @EnableWebSecurity: Enables Spring Security functionality and MVC integration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * In-Memory User Details Manager defining hardcoded users without database requirement.
     * - User A (Admin): username 'admin', password 'admin123', Role 'ADMIN'
     * - User B (Normal User): username 'user', password 'user123', Role 'USER'
     *
     * @param passwordEncoder the BCrypt password encoder bean used to hash passwords
     * @return UserDetailsService managing in-memory users
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Define Admin User with ADMIN role
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // Define Normal User with USER role
        UserDetails normalUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    /**
     * PasswordEncoder Bean using BCrypt hashing.
     * Encrypts plain-text passwords before storing in memory and matches incoming Basic Auth credentials.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) allowing React frontend
     * running on localhost dev server (e.g. port 6100) to call backend APIs.
     *
     * @return CorsConfigurationSource instance
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from React dev servers on localhost
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configures HTTP security filter chain: CORS, CSRF, authorization rules, and Basic Auth.
     *
     * @param http HttpSecurity configuration object provided by Spring Security
     * @return SecurityFilterChain instance built by HttpSecurity
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS using our custom CorsConfigurationSource bean above
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Disable CSRF for stateless REST API endpoints using HTTP Basic Auth
            .csrf(csrf -> csrf.disable())
            // Configure endpoint-level authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow public access to endpoint /api/f3
                .requestMatchers("/api/f3").permitAll()
                
                // Allow public access to Swagger UI and OpenAPI documentation
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()
                
                // Restrict /api/f1 to users with ADMIN role only (admin/admin123)
                .requestMatchers("/api/f1").hasRole("ADMIN")
                
                // Restrict /api/f2 to users with USER role only (user/user123)
                .requestMatchers("/api/f2").hasRole("USER")
                
                // Require authentication for any other unlisted endpoints
                .anyRequest().authenticated()
            )
            // Enable HTTP Basic Authentication mechanism
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
