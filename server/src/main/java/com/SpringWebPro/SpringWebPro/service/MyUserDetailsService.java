package com.SpringWebPro.SpringWebPro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringWebPro.SpringWebPro.models.UserPrincipal;
import com.SpringWebPro.SpringWebPro.models.Users;
import com.SpringWebPro.SpringWebPro.repository.UserRepo;

/**
 * Custom User Details Service
 * 
 * Implements Spring Security's UserDetailsService interface.
 * This is how Spring Security loads user data from our database.
 * 
 * Called during:
 * - Login authentication (by AuthenticationManager)
 * - JWT validation (by JwtFilter)
 * 
 * Returns UserDetails object which Spring Security uses to:
 * - Compare passwords during login
 * - Check user authorities/roles
 * - Verify account status (enabled, locked, expired)
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    
    // Repository to access user data from database
    @Autowired
    private UserRepo repo;

    /**
     * Load User by Username
     * 
     * Required method from UserDetailsService interface.
     * Spring Security calls this to get user details during authentication.
     * 
     * Process:
     * 1. Query database for user with given username
     * 2. If not found, throw UsernameNotFoundException
     * 3. If found, wrap in UserPrincipal (implements UserDetails)
     * 4. Spring Security uses this to validate credentials
     * 
     * @param username - the username to search for
     * @return UserDetails - Spring Security's user representation
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Query database for user
        Users user = repo.findByUsername(username);
        
        // User not found - throw exception (Spring Security will handle it)
        if (user == null) {
            throw new UsernameNotFoundException("User 404");
        }
        
        // Wrap our User entity in UserPrincipal (implements UserDetails)
        // This provides Spring Security with password, authorities, etc.
        return new UserPrincipal(user);
    }
}
