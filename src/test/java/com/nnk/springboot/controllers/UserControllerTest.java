package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

    private List<User> userList;

    @BeforeEach
    void beforeEach() {
        userList = new ArrayList<>();
        user1 = new User("username1", "Password1$", "fullname1", "ROLE_USER");
        user2 = new User("username2", "Password2$", "fullname2", "ROLE_ADMIN");
        user3 = new User("username3", "Password3$", "fullname3", "ROLE_USER");
        user4 = new User("username4", "Password4$", "fullname4", "ROLE_USER");
        user5 = new User(null, null, null, null);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedTest() throws Exception {
        when(userService.findAllUser()).thenReturn(userList);
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().attribute("userList", userList))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("user/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD USER FORM TEST //

    @Test
    @WithMockUser
    void addUserFormAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addUserForm"))
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithAnonymousUser
    void addUserFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user4);
        mockMvc.perform(post("/user/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("userList", user4)
                .param("fullname", user4.getFullname())
                .param("username", user4.getUsername())
                .param("password", user4.getPassword())
                .param("role", user4.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/user/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("user", user5)
                .param("fullname", user5.getFullname())
                .param("username", user5.getUsername())
                .param("password", user5.getPassword())
                .param("role", user5.getRole()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/user/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("user", user4)
                .param("fullname", user4.getFullname())
                .param("username", user4.getUsername())
                .param("password", user4.getPassword())
                .param("role", user4.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedUserIdExistsTest() throws Exception {
        when(userService.findUserById(anyInt())).thenReturn(user1);
        mockMvc.perform(get("/user/update/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("user", user1))
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedUserIdNotExistsTest() throws Exception {
        when(userService.findUserById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/user/update/{id}", 1)
                .param("userId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/user/update/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE USER TEST //

    @Test
    @WithMockUser
    void updateUserAuthenticatedValidDataTest() throws Exception {
        when(userService.findUserById(anyInt())).thenReturn(user1);
        when(userService.saveUser(any(User.class))).thenReturn(user1);
        mockMvc.perform(post("/user/update/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1")
                .param("fullname", user1.getFullname())
                .param("username", user1.getUsername())
                .param("password", user1.getPassword())
                .param("role", user1.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateUser"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser
    void updateUserAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 5)
                .sessionAttr("user", user5)
                .param("userId", "5")
                .param("fullname", user5.getFullname())
                .param("username", user5.getUsername())
                .param("password", user5.getPassword())
                .param("role", user5.getRole()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateUser"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithAnonymousUser
    void updateUserUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE USER TEST //

    @Test
    @WithMockUser
    void deleteUserAuthenticatedBidListIdExistsTest() throws Exception {
        when(userService.findUserById(anyInt())).thenReturn(user1);
        doNothing().when(userService).deleteUser(any(User.class));
        mockMvc.perform(get("/user/delete/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteUser"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser
    void deleteUserAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(userService.findUserById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/user/delete/{id}", 1)
                .param("userId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteUser"));
    }

    @Test
    @WithAnonymousUser
    void deleteUserUnauthenticatedBidListIdExistsTest() throws Exception {
        mockMvc.perform(get("/user/delete/{id}",1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
