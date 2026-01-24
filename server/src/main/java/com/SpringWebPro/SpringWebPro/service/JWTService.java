package com.SpringWebPro.SpringWebPro.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT (JSON Web Token) Service
 * 
 * Handles all JWT operations:
 * - Token generation (when user logs in)
 * - Token validation (on every authenticated request)
 * - Extracting claims from token (username, expiration, etc.)
 * 
 * JWT Structure: Header.Payload.Signature
 * - Header: Algorithm and token type
 * - Payload: Claims (username, issued at, expiration)
 * - Signature: Ensures token hasn't been tampered with
 * 
 * IMPORTANT: Change secretKey in production to a secure random value!
 */
@Service
public class JWTService {
    
    // Secret key for signing tokens (Base64 encoded)
    // WARNING: Change this in production! Use environment variable or secure vault
    private String secretKey = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Generate JWT Token
     * 
     * Creates a new JWT token for authenticated user.
     * Token contains:
     * - Subject: username
     * - Issued At: current timestamp
     * - Expiration: 10 hours from now
     * - Signature: signed with secret key using HS256 algorithm
     * 
     * @param username - the username to embed in token
     * @return String - the JWT token (e.g., "eyJhbGciOiJIUzI1NiJ9...")
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); // Additional claims (currently empty)
        return Jwts.builder()
                .setClaims(claims) // Set custom claims (if any)
                .setSubject(username) // Set username as subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token creation time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expires in 10 hours
                .signWith(getKey(), SignatureAlgorithm.HS256) // Sign with secret key
                .compact(); // Build the token
    }

    /**
     * Get Secret Key for Signing
     * 
     * Decodes the Base64 secret key and creates a SecretKey object.
     * Used for both signing (creating) and verifying tokens.
     * 
     * @return SecretKey object for JWT operations
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract Username from Token
     * 
     * Parses the JWT token and extracts the username (subject).
     * Used by JwtFilter to identify the user making the request.
     * 
     * @param token - the JWT token
     * @return String - the username from token
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract Specific Claim from Token
     * 
     * Generic method to extract any claim from token.
     * Uses Function to specify which claim to extract.
     * 
     * @param token - the JWT token
     * @param claimResolver - function to extract specific claim
     * @return T - the extracted claim value
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extract All Claims from Token
     * 
     * Parses and validates the JWT token, returning all claims.
     * Verifies signature using secret key.
     * Throws exception if token is invalid or expired.
     * 
     * @param token - the JWT token
     * @return Claims object containing all token data
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey()) // Use secret key to verify signature
                .build()
                .parseClaimsJws(token) // Parse and verify token
                .getBody(); // Get claims (payload)
    }

    /**
     * Validate JWT Token
     * 
     * Checks if token is valid for the given username.
     * Validation criteria:
     * 1. Username in token matches expected username
     * 2. Token is not expired
     * 
     * @param token - the JWT token to validate
     * @param username - expected username
     * @return boolean - true if token is valid, false otherwise
     */
    public boolean validateToken(String token, String username) {
        final String userName = extractUserName(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * Check if Token is Expired
     * 
     * Compares token's expiration time with current time.
     * 
     * @param token - the JWT token
     * @return boolean - true if expired, false if still valid
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract Expiration Date from Token
     * 
     * Gets the expiration timestamp from token claims.
     * 
     * @param token - the JWT token
     * @return Date - the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
