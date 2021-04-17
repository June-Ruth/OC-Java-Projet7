package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private User user1;
    private User user2;
    private User user3;
    private User user4;

    private List<User> userList;

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-test.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userList = new ArrayList<>();
        user1 = new User("User", "Password1$", "User Test", "ROLE_USER");
        user2 = new User("Admin", "Password2$", "Admin Test", "ROLE_ADMIN");
        user3 = new User("Test", "Password3$", "Test", "ROLE_USER");
        user4 = new User(null, null, null, null);
        userList.add(user1);
        userList.add(user2);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("user/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD USER FORM TEST //

    @Test
    @WithMockUser
    void addUserFormAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addUserForm"))
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithAnonymousUser
    void addUserFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/user/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("userList", user3)
                .param("fullname", user3.getFullname())
                .param("username", user3.getUsername())
                .param("password", user3.getPassword())
                .param("role", user3.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/user/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("user", user4)
                .param("fullname", user4.getFullname())
                .param("username", user4.getUsername())
                .param("password", user4.getPassword())
                .param("role", user4.getRole()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(get("/user/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("user", user3)
                .param("fullname", user3.getFullname())
                .param("username", user3.getUsername())
                .param("password", user3.getPassword())
                .param("role", user3.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedUserIdExistsIT() throws Exception {
        mockMvc.perform(get("/user/update/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedUserIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/user/update/{id}", 17)
                .param("userId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/user/update/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE USER TEST //

    @Test
    @WithMockUser
    void updateUserAuthenticatedValidDataIT() throws Exception {
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
    void updateUserAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 1)
                .sessionAttr("user", user1)
                .param("userId", "1")
                .param("fullname", user4.getFullname())
                .param("username", user4.getUsername())
                .param("password", user4.getPassword())
                .param("role", user4.getRole()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateUser"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithAnonymousUser
    void updateUserUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/user/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE USER TEST //

    @Test
    @WithMockUser
    void deleteUserAuthenticatedBidListIdExistsIT() throws Exception {
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
    void deleteUserAuthenticatedBidListIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/user/delete/{id}", 17)
                .param("userId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteUser"));
    }

    @Test
    @WithAnonymousUser
    void deleteUserUnauthenticatedBidListIdExistsIT() throws Exception {
        mockMvc.perform(get("/user/delete/{id}",1)
                .sessionAttr("user", user1)
                .param("userId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
