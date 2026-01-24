package com.SpringWebPro.SpringWebPro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.SpringWebPro.SpringWebPro.models.Users;
import com.SpringWebPro.SpringWebPro.repository.UserRepo;

/**
 * User Service Layer
 * 
 * Business logic for user operations:
 * - User registration (with password encryption)
 * - User authentication (login verification)
 * - JWT token generation for authenticated users
 * 
 * This service sits between controllers and repositories,
 * handling the core authentication logic.
 */
@Service
public class UserService {
    
    // Repository for database operations
    @Autowired
    private UserRepo userRepo;

    // Service to generate JWT tokens
    @Autowired
    private JWTService jwtService;

    // Spring Security's authentication manager
    @Autowired
    private AuthenticationManager authManager;

    // Password encoder - BCrypt with strength 12 (4096 rounds)
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Register New User
     * 
     * Creates a new user account with encrypted password.
     * Password is never stored in plain text!
     * 
     * Process:
     * 1. Take plain text password from user object
     * 2. Hash it using BCrypt (one-way encryption)
     * 3. Replace plain password with hashed version
     * 4. Save user to database
     * 
     * Example:
     * Input password: "secret123"
     * Stored password: "$2a$12$N9qo8uLOickgx2ZMRZoMye..." (60 characters)
     * 
     * @param user - User object with username and plain password
     * @return Users - saved user object (with encrypted password)
     */
    public Users register(Users user) {
        // Encrypt password before saving (one-way hash)
        user.setPassword(encoder.encode(user.getPassword()));
        // Save to database and return
        return userRepo.save(user);
    }

    /**
     * Verify User Credentials and Generate Token
     * 
     * Authenticates user login attempt.
     * 
     * Process:
     * 1. Create authentication token with username and password
     * 2. AuthenticationManager validates credentials:
     *    - Loads user from database (via MyUserDetailsService)
     *    - Compares hashed passwords
     * 3. If valid, generate JWT token
     * 4. If invalid, throws exception (caught by controller)
     * 
     * @param user - User object with username and password
     * @return String - JWT token if authenticated, "Fail" otherwise
     */
    public String verify(Users user) {
        // Attempt to authenticate user
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // If authentication successful, generate JWT token
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        // Return failure message (should rarely happen as invalid auth throws exception)
        return "Fail";
    }
}
