package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UsernameAlreadyExistException;

import java.util.List;

public interface UserService {
    /**
     * Find a user by its id.
     * @param id .
     * @return user found - if not found, throws ElementNotFoundException
     * @see User
     */
    User findUserById(Integer id);

    /**
     * Find all user.
     * @return a list with all user.
     * @see User
     */
    List<User> findAllUser();

    /**
     * Save a new user or update an existing user.
     * @param user to save.
     * @return user saved.
     * @see User
     */
    User saveUser(User user) throws UsernameAlreadyExistException;

    /**
     * Delete an existing user.
     * @param user to delete.
     */
    void deleteUser(User user);

    /**
     * Check the presence of user with same username.
     * @param username .
     * @return true if exists.
     */
    boolean checkUserByUsername(String username);
}
