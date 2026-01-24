package com.SpringWebPro.SpringWebPro.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * User Entity Model
 * 
 * Represents a user in the database.
 * Used for authentication and authorization.
 * 
 * Database table: users
 * Columns:
 * - user_id (PRIMARY KEY, AUTO_INCREMENT)
 * - username (should be UNIQUE)
 * - password (stored as BCrypt hash, never plain text)
 * 
 * Note: Password is encrypted before storing (see UserService.register())
 */
@Entity // JPA annotation - this class maps to a database table
public class Users {
    
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private int userId;
    
    private String username; // Unique username for login
    
    private String password; // BCrypt hashed password (60 characters)

    // Getters and Setters
    // Required by JPA and Spring framework
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get Password (returns encrypted hash)
     * Never returns plain text password!
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set Password
     * Should be called with encrypted password (from UserService)
     * Never store plain text passwords!
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
