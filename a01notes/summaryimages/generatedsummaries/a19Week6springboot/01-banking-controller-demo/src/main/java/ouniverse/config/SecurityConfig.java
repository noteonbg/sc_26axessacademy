package ouniverse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security & CORS Configuration for Banking Controller Demo.
 * 
 * Features:
 * 1. Enables Spring Security.
 * 2. Universal CORS enabled allowing requests from any origin URL (*), HTTP method, and header.
 * 3. Open authorization setup ready for role-based security rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    // hey spring ssecurity who needs security I will tell you givng this object called
    //SecurityfilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS support using the CorsConfigurationSource bean below
            .cors(Customizer.withDefaults())
            
            // Disable CSRF for REST APIs
            .csrf(AbstractHttpConfigurer::disable)
            
            // Permit endpoints for development (authorization to be configured later)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/**").permitAll()
                .requestMatchers("/customers/**").permitAll()
                .anyRequest().permitAll()
            );

        return http.build();
    }

    /**
     * Enables CORS for ANY origin / URL, HTTP method, and header.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow any origin URL
        configuration.setAllowedOriginPatterns(List.of("*"));
        
        // Allow all HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));
        
        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));
        
        // Allow credentials (cookies / auth headers)
        configuration.setAllowCredentials(true);
        
        // Expose headers
        configuration.setExposedHeaders(List.of("*"));
        
        // Cache preflight check for 1 hour
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
