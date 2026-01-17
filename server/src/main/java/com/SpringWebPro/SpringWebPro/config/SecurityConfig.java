// package com.SpringWebPro.SpringWebPro.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

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
