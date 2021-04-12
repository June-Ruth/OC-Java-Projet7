package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.exceptions.UsernameAlreadyExistException;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(UserServiceImpl.class);
    /**
     * @see UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Autowired constructor.
     * @param pUserRepository .
     */
    public UserServiceImpl(final UserRepository pUserRepository) {
        userRepository = pUserRepository;
    }

    /**
     * @see UserService .
     * @param id .
     * @return user
     */
    @Override
    public User findUserById(final Integer id) {
        LOGGER.info("Try to find user with id : " + id);
        User result = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No user found for id : " + id));
        LOGGER.info("Get user with id : " + id + "\n result : " + result);
        return result;
    }

    /**
     * @see UserService .
     * @return list of user.
     */
    @Override
    public List<User> findAllUser() {
        LOGGER.info("Try to get all user");
        List<User> result = userRepository.findAll();
        LOGGER.info("Get all user, size :" + result.size());
        return result;
    }

    /**
     * @see UserService .
     * @param user to save.
     * @return user.
     */
    @Transactional
    @Override
    public User saveUser(final User user) {
        LOGGER.info("Try to save user : " + user);
        if(!userRepository.findByUsername(user.getUsername()).isPresent()) {
            User result = userRepository.save(user);
            LOGGER.info("Save user : " + result);
            return result;
        } else {
            Integer id = userRepository.findByUsername(user.getUsername()).get().getId();
            if (user.getId() != null && user.getId().equals(id)) {
                User result = userRepository.save(user);
                LOGGER.info("Save user : " + result);
                return result;
            } else {
                throw new UsernameAlreadyExistException("username " + user.getUsername() + " is already used");
            }
        }
    }

    /**
     * @see UserService .
     * @param user to delete.
     */
    @Transactional
    @Override
    public void deleteUser(final User user) {
        LOGGER.info("Try to delete user : " + user);
        userRepository.delete(user);
        LOGGER.info("Delete user");
    }

    @Override
    public boolean checkUserByUsername(String username) {
        LOGGER.info("Check if user exists with username : " + username);
        return userRepository.existsByUsername(username);
    }
}
