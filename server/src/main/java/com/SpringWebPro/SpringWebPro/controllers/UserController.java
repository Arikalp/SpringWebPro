package com.SpringWebPro.SpringWebPro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringWebPro.SpringWebPro.models.Users;
import com.SpringWebPro.SpringWebPro.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * User Authentication Controller
 * 
 * Handles user registration and login endpoints.
 * These are public endpoints (configured in SecurityConfig) that don't require authentication.
 * 
 * Endpoints:
 * - POST /api/register - Create a new user account
 * - POST /api/login - Authenticate and get JWT token
 */
@RestController
@RequestMapping("/api")
@CrossOrigin // Allow requests from React frontend (different port)
public class UserController {

    // Service layer for user operations (register, login)
    @Autowired
    private UserService userService;

    /**
     * User Registration Endpoint
     * 
     * Creates a new user account with encrypted password.
     * Password is hashed using BCrypt before storing in database.
     * 
     * Request body: { "username": "john", "password": "secret123" }
     * Success response: { "message": "User registered successfully", "username": "john" }
     * Error response: { "error": "Registration failed: reason" }
     * 
     * @param user - User object from request body (username and password)
     * @return ResponseEntity with success message or error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            // Register user (password will be encrypted in service layer)
            Users newUser = userService.register(user);
            
            // Build success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", newUser.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Build error response (e.g., username already exists)
            Map<String, String> error = new HashMap<>();
            error.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * User Login Endpoint
     * 
     * Authenticates user and returns JWT token if credentials are valid.
     * Token expires after 10 hours (configurable in JWTService).
     * 
     * Request body: { "username": "john", "password": "secret123" }
     * Success response: { "token": "eyJhbGc...", "username": "john" }
     * Error response: { "error": "Invalid credentials" }
     * 
     * Client should:
     * 1. Store the token in localStorage
     * 2. Include token in Authorization header for subsequent requests:
     *    Authorization: Bearer <token>
     * 
     * @param user - User object from request body (username and password)
     * @return ResponseEntity with JWT token or error
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            // Verify credentials and get JWT token
            String token = userService.verify(user);
            
            // Check if authentication failed
            if ("Fail".equals(token)) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
            
            // Build success response with JWT token
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Build error response (e.g., user not found, wrong password)
            Map<String, String> error = new HashMap<>();
            error.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}
