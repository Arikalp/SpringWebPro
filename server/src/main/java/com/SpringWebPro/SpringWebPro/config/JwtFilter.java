package com.SpringWebPro.SpringWebPro.config;

import com.SpringWebPro.SpringWebPro.service.JWTService;
import com.SpringWebPro.SpringWebPro.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter
 * 
 * This filter intercepts every HTTP request and validates the JWT token.
 * It runs once per request (OncePerRequestFilter) before the request reaches the controller.
 * 
 * Flow:
 * 1. Extract JWT token from Authorization header
 * 2. Extract username from token
 * 3. Load user details from database
 * 4. Validate token against user details
 * 5. Set authentication in SecurityContext if valid
 * 6. Continue with the request
 * 
 * If token is invalid or missing, request continues but without authentication
 * (will be blocked by SecurityConfig if endpoint requires authentication)
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    // Service to handle JWT operations (generate, validate, extract claims)
    @Autowired
    private JWTService jwtService;

    // Spring context to get beans (used to get MyUserDetailsService)
    @Autowired
    ApplicationContext context;

    /**
     * Filter method that runs on every HTTP request
     * 
     * Steps:
     * 1. Check if Authorization header exists and starts with "Bearer "
     * 2. Extract the JWT token (remove "Bearer " prefix)
     * 3. Extract username from token
     * 4. If username exists and user is not already authenticated:
     *    - Load full user details from database
     *    - Validate the token
     *    - If valid, set authentication in SecurityContext
     * 5. Continue with the filter chain (next filter or controller)
     * 
     * @param request - HTTP request object
     * @param response - HTTP response object
     * @param filterChain - chain of filters to continue processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Get Authorization header from request (format: "Bearer <token>")
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Extract token if header exists and has correct format
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remove "Bearer " (7 characters)
            username = jwtService.extractUserName(token); // Extract username from JWT
        }

        // If we have a username and user is not already authenticated in this request
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from database
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            
            // Validate token (checks signature and expiration)
            if (jwtService.validateToken(token, userDetails.getUsername())) {
                // Create authentication token with user details and authorities
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Set additional details (IP address, session ID, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Store authentication in SecurityContext - now user is authenticated!
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
