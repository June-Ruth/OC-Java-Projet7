package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Rating;
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
class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Rating rating1;
    private Rating rating2;
    private Rating rating3;
    private Rating rating4;

    private List<Rating> ratingList;

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-test.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rating1 = new Rating("Moodys Rating 1", "Sand P Rating 1", "Fitch Rating 1", 1);
        rating2 = new Rating("Moodys Rating 2", "Sand P Rating 2", "Fitch Rating 2", 1);
        rating3 = new Rating("Moodys Rating 3", "Sand P Rating 3", "Fitch Rating 3", 1);
        rating4 = new Rating("Moodys5", "................................................................................................................................................................................................................................................................", "Fitch5", 5);
        ratingList = new ArrayList<>();
        ratingList.add(rating1);
        ratingList.add(rating2);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD RATING FORM TEST //

    @Test
    @WithMockUser
    void addRatingFormAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addRatingForm"))
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithAnonymousUser
    void addRatingFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("rating", rating3)
                .param("moodysRating", rating3.getMoodysRating())
                .param("sandPRating", rating3.getSandPRating())
                .param("fitchRating", rating3.getFitchRating())
                .param("orderNumber", rating3.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("rating", rating4)
                .param("moodysRating", rating4.getMoodysRating())
                .param("sandPRating", rating4.getSandPRating())
                .param("fitchRating", rating4.getFitchRating())
                .param("orderNumber", rating4.getOrderNumber().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(get("/rating/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("rating", rating3)
                .param("moodysRating", rating3.getMoodysRating())
                .param("sandPRating", rating3.getSandPRating())
                .param("fitchRating", rating3.getFitchRating())
                .param("orderNumber", rating3.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedRatingIdExistsIT() throws Exception {
        mockMvc.perform(get("/rating/update/{id}", 1)
                .param("ratingId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedRatingIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/rating/update/{id}", 17)
                .param("ratingId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/rating/update/{id}", 1)
                .sessionAttr("rating", rating1)
                .param("ratingId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE RATING TEST //

    @Test
    @WithMockUser
    void updateRatingAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 1)
                .sessionAttr("rating", rating1)
                .param("moodysRating", rating1.getMoodysRating())
                .param("sandPRating", rating1.getSandPRating())
                .param("fitchRating", rating1.getFitchRating())
                .param("orderNumber", rating1.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateRating"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser
    void updateRatingAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 1)
                .sessionAttr("rating", rating1)
                .param("moodysRating", rating4.getMoodysRating())
                .param("sandPRating", rating4.getSandPRating())
                .param("fitchRating", rating4.getFitchRating())
                .param("orderNumber", rating4.getOrderNumber().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateRating"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithAnonymousUser
    void updateRatingUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE RATING TEST //

    @Test
    @WithMockUser
    void deleteRatingAuthenticatedRatingIdExistsIT() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}", 1)
                .sessionAttr("rating", rating1)
                .param("ratingId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteRating"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser
    void deleteRatingAuthenticatedRatingIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}", 17)
                .param("ratingId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteRating"));
    }

    @Test
    @WithAnonymousUser
    void deleteRatingUnauthenticatedRatingIdExistsIT() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}",1)
                .sessionAttr("rating", rating1)
                .param("ratingId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
