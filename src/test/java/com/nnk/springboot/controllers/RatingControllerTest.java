package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    private Rating rating1;
    private Rating rating2;
    private Rating rating3;
    private Rating rating4;
    private Rating rating5;

    private List<Rating> ratingList;

    @BeforeEach
    void beforeEach() {
        ratingList = new ArrayList<>();
        rating1 = new Rating("Moodys1", "SandP1", "Fitch1", 1);
        rating2 = new Rating("Moodys2", "SandP2", "Fitch2", 2);
        rating3 = new Rating("Moodys3", "SandP3", "Fitch3", 3);
        rating4 = new Rating("Moodys4", "SandP4", "Fitch4", 4);
        rating5 = new Rating("Moodys5", "................................................................................................................................................................................................................................................................", "Fitch5", 5);
        ratingList.add(rating1);
        ratingList.add(rating2);
        ratingList.add(rating3);
        ratingList.add(rating4);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedTest() throws Exception {
        when(ratingService.findAllRating()).thenReturn(ratingList);
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().attribute("ratingList", ratingList))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD RATING FORM TEST //

    @Test
    @WithMockUser
    void addRatingFormAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addRatingForm"))
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithAnonymousUser
    void addRatingFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        when(ratingService.saveRating(any(Rating.class))).thenReturn(rating4);
        mockMvc.perform(post("/rating/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("rating", rating4)
                .param("moodysRating", rating4.getMoodysRating())
                .param("sandPRating", rating4.getSandPRating())
                .param("fitchRating", rating4.getFitchRating())
                .param("orderNumber", rating4.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("rating", rating5)
                .param("moodysRating", rating5.getMoodysRating())
                .param("sandPRating", rating5.getSandPRating())
                .param("fitchRating", rating5.getFitchRating())
                .param("orderNumber", rating5.getOrderNumber().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/rating/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("rating", rating4)
                .param("moodysRating", rating4.getMoodysRating())
                .param("sandPRating", rating4.getSandPRating())
                .param("fitchRating", rating4.getFitchRating())
                .param("orderNumber", rating4.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedCurvePointIdExistsTest() throws Exception {
        when(ratingService.findRatingById(anyInt())).thenReturn(rating4);
        mockMvc.perform(get("/rating/update/{id}", 4)
                .param("ratingId", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("rating", rating4))
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedCurvePointIdNotExistsTest() throws Exception {
        when(ratingService.findRatingById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/rating/update/{id}", 1)
                .param("curvePointId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/rating/update/{id}", 1)
                .sessionAttr("rating", rating1)
                .param("ratingId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE RATING TEST //

    @Test
    @WithMockUser
    void updateRatingAuthenticatedValidDataTest() throws Exception {
        when(ratingService.findRatingById(anyInt())).thenReturn(rating1);
        when(ratingService.saveRating(any(Rating.class))).thenReturn(rating4);
        mockMvc.perform(post("/rating/update/{id}", 4)
                .sessionAttr("rating", rating4)
                .param("moodysRating", rating4.getMoodysRating())
                .param("sandPRating", rating4.getSandPRating())
                .param("fitchRating", rating4.getFitchRating())
                .param("orderNumber", rating4.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateRating"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser
    void updateRatingAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 2)
                .sessionAttr("rating", rating5)
                .param("moodysRating", rating5.getMoodysRating())
                .param("sandPRating", rating5.getSandPRating())
                .param("fitchRating", rating5.getFitchRating())
                .param("orderNumber", rating5.getOrderNumber().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateRating"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithAnonymousUser
    void updateRatingUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE RATING TEST //

    @Test
    @WithMockUser
    void deleteRatingAuthenticatedBidListIdExistsTest() throws Exception {
        when(ratingService.findRatingById(anyInt())).thenReturn(rating1);
        doNothing().when(ratingService).deleteRating(any(Rating.class));
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
    void deleteRatingAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(ratingService.findRatingById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/rating/delete/{id}", 1)
                .param("ratingId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteRating"));
    }

    @Test
    @WithAnonymousUser
    void deleteRatingUnauthenticatedBidListIdExistsTest() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}",1)
                .sessionAttr("rating", rating1)
                .param("ratingId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
