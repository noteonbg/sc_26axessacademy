package com.standardchartered.jwtdemo.config;

import com.standardchartered.jwtdemo.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @Configuration: Marks this class as a Spring Configuration container.
 * @EnableWebSecurity: Enables Spring Security 6 web security and Spring MVC integration.
 */
@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public JwtSecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * PasswordEncoder Bean using BCrypt hashing algorithm for securing user passwords.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager Bean used in AuthController to authenticate user credentials during login.
     *
     * @param config Spring Security's AuthenticationConfiguration
     * @return AuthenticationManager instance
     * @throws Exception if manager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) allowing React frontend
     * running on localhost dev servers (e.g. ports 3000, 5173) to send cross-origin requests
     * with Authorization Bearer headers.
     *
     * @return CorsConfigurationSource instance
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from React dev servers
        configuration.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * SecurityFilterChain configuration defining CORS, CSRF, Stateless session policy,
     * request authorization rules, and registering JwtAuthenticationFilter.
     *
     * @param http HttpSecurity configuration builder
     * @return SecurityFilterChain instance
     * @throws Exception if security setup fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Enable CORS using our custom CorsConfigurationSource bean above
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // Disable CSRF for stateless REST APIs using JWT Bearer Tokens
                .csrf(csrf -> csrf.disable())
                
                // Configure frame options for H2 Console access
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                
                // Configure Stateless session policy (No HTTP JSESSIONID cookies created)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // Define endpoint authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints: Login, Register, H2 Console, and Swagger UI
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/h2-console/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        
                        // Admin restricted endpoints: Requires ROLE_ADMIN
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        
                        // Banking account endpoints: Requires ROLE_CUSTOMER or ROLE_ADMIN
                        .requestMatchers("/api/v1/account/**").hasAnyRole("CUSTOMER", "ADMIN")
                        
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                
                // Register custom JWT Authentication Filter before standard UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Swagger OpenAPI v3 configuration adding Bearer JWT Security Scheme.
     *
     * @return OpenAPI configuration object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter your JWT Token obtained from /api/v1/auth/login")));
    }
}
