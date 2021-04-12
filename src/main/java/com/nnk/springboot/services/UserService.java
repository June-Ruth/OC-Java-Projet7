package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {
    /**
     * Find a trade by its id.
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
    User saveUser(User user);

    /**
     * Delete an existing user.
     * @param user to delete.
     */
    void deleteUser(User user);
}
