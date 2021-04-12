package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private static UserRepository userRepository;

    private static UserService userService;

    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    void beforeEach() {
        userService = new UserServiceImpl(userRepository);
        userList = new ArrayList<>();
        user1 = new User("username1", "password1", "fullname1", "ROLE_USER");
        user2 = new User("username2", "password2", "fullname2", "ROLE_ADMIN");
        userList.add(user1);
        userList.add(user2);
    }

    // FIND USER BY ID TEST //

    @Test
    void findUserByIdExistsTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user1));
        userService.findUserById(1);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void findUserByIdNotExistsTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> userService.findUserById(1));
    }

    // FIND ALL USER TEST //

    @Test
    void findAllUserTest() {
        when(userRepository.findAll()).thenReturn(userList);
        userService.findAllUser();
        verify(userRepository, times(1)).findAll();
    }

    // SAVE USER TEST //

    @Test
    void saveUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        userService.saveUser(user1);
        verify(userRepository, times(1)).save(user1);
    }

    // DELETE USER TEST //

    @Test
    void deleteUserTest() {
        doNothing().when(userRepository).delete(any(User.class));
        userService.deleteUser(user1);
        verify(userRepository, times(1)).delete(user1);
    }
}
