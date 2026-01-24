package com.SpringWebPro.SpringWebPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SpringWebPro.SpringWebPro.models.Users;

/**
 * User Repository Interface
 * 
 * Data Access Layer for User entity.
 * Extends JpaRepository which provides:
 * - save() - insert or update user
 * - findById() - find user by ID
 * - findAll() - get all users
 * - delete() - delete user
 * - and many more...
 * 
 * Spring Data JPA automatically implements this interface at runtime.
 * No need to write SQL queries!
 */
@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    
    /**
     * Find User by Username
     * 
     * Spring Data JPA automatically generates the SQL query:
     * SELECT * FROM users WHERE username = ?
     * 
     * Method naming convention:
     * findBy + FieldName = query by that field
     * 
     * Used by:
     * - MyUserDetailsService (to load user during authentication)
     * 
     * @param username - the username to search for
     * @return Users object if found, null if not found
     */
    Users findByUsername(String username);
}
