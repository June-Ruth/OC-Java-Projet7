package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository
        extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    /**
     * Find by username.
     * @param username .
     * @return User.
     */
    Optional<User> findByUsername(String username);

    /**
     * Exists by username.
     * @param username .
     * @return true if exists.
     */
    boolean existsByUsername(String username);
}
