package com.SpringWebPro.SpringWebPro.models;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User Principal - Adapter for Spring Security
 * 
 * This class wraps our Users entity and implements Spring Security's UserDetails interface.
 * Spring Security uses UserDetails to get user information during authentication.
 * 
 * Why do we need this?
 * - Spring Security expects UserDetails interface
 * - Our Users entity is just a database model
 * - UserPrincipal bridges the gap between our model and Spring Security
 * 
 * Think of it as: "This is how Spring Security sees our User"
 */
public class UserPrincipal implements UserDetails {

    // Our actual user entity from database
    private Users user;

    /**
     * Constructor
     * Wraps our Users entity for Spring Security
     */
    public UserPrincipal(Users user) {
        this.user = user;
    }
    
    /**
     * Get User Authorities (Roles/Permissions)
     * 
     * Returns the roles/permissions this user has.
     * Currently all users get "USER" role.
     * 
     * Future enhancement: Store roles in database and return actual user roles
     * Example: ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR
     * 
     * @return Collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Currently all users have "USER" authority
        // TODO: Implement role-based access control (RBAC)
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    /**
     * Get Password (Encrypted)
     * 
     * Spring Security uses this to compare with entered password during login.
     * Returns BCrypt hash, not plain text!
     * 
     * @return String - encrypted password from database
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get Username
     * 
     * Spring Security uses this as the principal identifier.
     * 
     * @return String - username from database
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Is Account Non-Expired?
     * 
     * Could be used to implement account expiration (e.g., trial accounts).
     * Currently always returns true (accounts never expire).
     * 
     * @return boolean - true if account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Accounts don't expire in current implementation
    }

    /**
     * Is Account Non-Locked?
     * 
     * Could be used to implement account locking (e.g., after failed login attempts).
     * Currently always returns true (accounts are never locked).
     * 
     * @return boolean - true if account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Accounts are never locked in current implementation
    }

    /**
     * Are Credentials Non-Expired?
     * 
     * Could be used to force password changes after certain period.
     * Currently always returns true (passwords never expire).
     * 
     * @return boolean - true if credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Passwords don't expire in current implementation
    }

    /**
     * Is Account Enabled?
     * 
     * Could be used to implement account activation (e.g., email verification).
     * Currently always returns true (all accounts are enabled).
     * 
     * @return boolean - true if account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true; // All accounts are enabled in current implementation
    }
}
