package com.SpringWebPro.SpringWebPro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration Class
 * 
 * This class configures Spring Security for JWT-based authentication.
 * It defines which endpoints are public vs protected, and sets up the authentication flow.
 * 
 * Key concepts:
 * - JWT (JSON Web Token): Stateless authentication mechanism
 * - Filter Chain: Intercepts HTTP requests to validate tokens
 * - Authentication Provider: Handles username/password validation
 * - BCrypt: Password hashing algorithm for secure storage
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Service to load user details from database
    @Autowired
    private UserDetailsService userDetailsService;

    // Custom filter to validate JWT tokens on each request
    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Security Filter Chain Configuration
     * 
     * This defines the security rules for HTTP requests:
     * 1. CSRF is disabled - not needed for stateless JWT authentication
     * 2. Public endpoints - /api/register and /api/login don't require authentication
     * 3. All other endpoints require authentication (valid JWT token)
     * 4. Session is STATELESS - no session cookies, only JWT tokens
     * 5. JwtFilter runs before UsernamePasswordAuthenticationFilter to validate tokens
     * 
     * @param http - HttpSecurity object to configure security rules
     * @return SecurityFilterChain - the configured security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF - we use JWT tokens which are immune to CSRF attacks
                .csrf(customizer -> customizer.disable())
                // Define which endpoints are public vs protected
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/register", "/api/login").permitAll() // Public endpoints
                        .anyRequest().authenticated()) // All other endpoints need authentication
                // Enable HTTP Basic auth as fallback
                .httpBasic(Customizer.withDefaults())
                // Make sessions stateless - we don't store session on server
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Add our JWT filter to validate tokens before Spring's authentication filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Authentication Provider Bean
     * 
     * Configures how Spring Security authenticates users.
     * Uses DAO (Data Access Object) pattern to load user from database.
     * 
     * @return DaoAuthenticationProvider configured with password encoder and user details service
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Set password encoder - BCrypt with strength 12 (2^12 = 4096 rounds)
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        // Set service to load user details from database
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Authentication Manager Bean
     * 
     * Required for programmatic authentication (used in login endpoint).
     * Manages the authentication process.
     * 
     * @param config - Spring's authentication configuration
     * @return AuthenticationManager instance
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Password Encoder Bean
     * 
     * BCrypt is a one-way hashing algorithm designed for passwords.
     * Strength 12 means 2^12 (4096) hashing rounds - good balance of security and performance.
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}


// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) // Disable CSRF for API endpoints (enable for production)
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/api/**").permitAll() // Allow all API endpoints
//                 .requestMatchers("/h2-console/**").permitAll() // Allow H2 console
//                 .anyRequest().authenticated()
//             )
//             .headers(headers -> headers
//                 .frameOptions(frame -> frame.sameOrigin()) // Allow H2 console frames
//             )
//             .formLogin(form -> form.permitAll())
//             .logout(logout -> logout.permitAll());
        
//         return http.build();
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails user = User.builder()
//             .username("user")
//             .password(passwordEncoder().encode("password"))
//             .roles("USER")
//             .build();
        
//         UserDetails admin = User.builder()
//             .username("admin")
//             .password(passwordEncoder().encode("admin"))
//             .roles("ADMIN")
//             .build();

//         return new InMemoryUserDetailsManager(user, admin);
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
