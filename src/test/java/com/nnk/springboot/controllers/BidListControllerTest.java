package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BidListController.class)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    private BidList bidList1;
    private BidList bidList2;
    private BidList bidList3;
    private BidList bidList4;
    private BidList bidList5;
    private BidList bidList6;

    private List<BidList> bidListList;

    @BeforeEach
    void beforeEach() {
        bidListList = new ArrayList<>();
        bidList1 = new BidList("Account 1", "Type 1", 11.00d);
        bidList2 = new BidList("Account 2", "Type 2", 22.00d);
        bidList3 = new BidList("Account 3", "Type 3", 33.00d);
        bidList4 = new BidList("Account 4", "Type 4", 44.00d);
        bidList5 = new BidList("Account 5", "Type 5", 55.00d);
        bidList6 = new BidList(null, null, 0d);
        bidListList.add(bidList1);
        bidListList.add(bidList2);
        bidListList.add(bidList3);
        bidListList.add(bidList4);

    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedTest() throws Exception {
        when(bidListService.findAllBidList()).thenReturn(bidListList);
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(request().attribute("bidListList", bidListList))
                .andExpect(view().name("bidList/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
//TODO : pour le moment, pas de redirection, voir avec la mise en place de spring sécu
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(redirectedUrl("/login"));
    }


    // ADD BID FORM TEST //

    @Test
    @WithMockUser
    void addBidFormAuthenticatedTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addBidForm"))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithAnonymousUser
    void addBidFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().is3xxRedirection())
//TODO : pour le moment, pas de redirection, voir avec la mise en place de spring sécu
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addBidForm"))
                .andExpect(redirectedUrl("/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(post("/bidList/validate")
                .content(new ObjectMapper().writeValueAsString(bidList5))
                .contentType("text/html;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(request().attribute("bidList", bidList5)) //TODO : vérifier le nom donné à l'attribut
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(post("/bidList/validate")
                .content(new ObjectMapper().writeValueAsString(bidList6))
                .contentType("text/html;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(view().name("bidList/add")); //TODO : voir si pas vue de prévu pour badrequest ?
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/bidList/validate")
                .content(new ObjectMapper().writeValueAsString(bidList5))
                .contentType("text/html;charset=UTF-8"))
                .andExpect(status().is3xxRedirection())
//TODO : pour le moment, pas de redirection, voir avec la mise en place de spring sécu
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdExistsTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("bidList", bidList5)) //TODO : voir ce qu'on met dedans
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdNotExistsTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("bidList", bidList5)) //TODO : voir ce qu'on met dedans
                .andExpect(view().name("bidList/update")); //TODO : voir quelle vue
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
//TODO : pour le moment, pas de redirection, voir avec la mise en place de spring sécu
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(redirectedUrl("/login"));
    }

    // UPDATE BID TEST //

    @Test
    @WithMockUser
    void updateBidAuthenticatedValidDataTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateBid"))
                .andExpect(request().attribute("bidList", bidList5)) //TODO : voir ce qu'on met dedans
                .andExpect(redirectedUrl("/bidList/List"));
    }

    @Test
    @WithMockUser
    void updateBidAuthenticatedInvalidDataTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateBid"))
                .andExpect(request().attribute("bidList", bidList5)) //TODO : voir ce qu'on met dedans
                .andExpect(redirectedUrl("/bidList/List"));
    }

    @Test
    @WithAnonymousUser
    void updateBidUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
//TODO : pour le moment, pas de redirection, voir avec la mise en place de spring sécu
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateBid"))
                .andExpect(redirectedUrl("/login"));
    }

    // DELETE BID //

    @Test
    @WithMockUser
    void deleteBidAuthenticatedBidListIdExistsTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("deleteBid"))
                .andExpect(request().attribute("bidListList", bidListList)) //TODO : voir ce qu'on met dedans
                .andExpect(redirectedUrl("/bidList/List"));
    }

    @Test
    @WithMockUser
    void deleteBidAuthenticatedBidListIdNotExistsTest() throws Exception {
        //TODO : when / return
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("deleteBid"))
                .andExpect(request().attribute("bidListList", bidListList)) //TODO : voir ce qu'on met dedans
                .andExpect(redirectedUrl("/bidList/List")); //TODO : voir quelle vue
    }

    @Test
    @WithAnonymousUser
    void deleteBidUnauthenticatedBidListIdExistsTest() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
//TODO : pour le moment, pas de redirection, voir avec la mise en place de spring sécu
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("deleteBid"))
                .andExpect(redirectedUrl("/login"));
    }

}
