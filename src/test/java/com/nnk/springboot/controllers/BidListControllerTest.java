package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.ElementNotFoundException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
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
        bidList4 = new BidList("Account 4", "Type 4", 44.50d);
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
                .andExpect(model().attribute("bidListList", bidListList))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD BID FORM TEST //

    @Test
    @WithMockUser
    void addBidFormAuthenticatedTest() throws Exception {
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
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        when(bidListService.saveBidList(any(BidList.class))).thenReturn(bidList4);
        mockMvc.perform(post("/bidList/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("bidList", bidList4)
                .param("account", bidList4.getAccount())
                .param("type", bidList4.getType())
                .param("bidQuantity", bidList4.getBidQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                //.content(new ObjectMapper().writeValueAsString(bidList6))
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("bidList", bidList6)
                .param("account", bidList6.getAccount())
                .param("type", bidList6.getType())
                .param("bidQuantity", bidList6.getBidQuantity().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/bidList/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("bidList", bidList4)
                .param("account", bidList4.getAccount())
                .param("type", bidList4.getType())
                .param("bidQuantity", bidList4.getBidQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdExistsTest() throws Exception {
        when(bidListService.findBidListById(anyInt())).thenReturn(bidList1);
        mockMvc.perform(get("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("bidList", bidList1))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(bidListService.findBidListById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/bidList/update/{id}", 1)
                .param("bidListId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE BID TEST //

    @Test
    @WithMockUser
    void updateBidAuthenticatedValidDataTest() throws Exception {
        when(bidListService.findBidListById(anyInt())).thenReturn(bidList1);
        when(bidListService.saveBidList(any(BidList.class))).thenReturn(bidList1);
        mockMvc.perform(post("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1")
                .param("account", bidList1.getAccount())
                .param("type", bidList1.getType())
                .param("bidQuantity", bidList1.getBidQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateBid"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser
    void updateBidAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1")
                .param("account", "")
                .param("type", "")
                .param("bidQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateBid"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithAnonymousUser
    void updateBidUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE BID //

    @Test
    @WithMockUser
    void deleteBidAuthenticatedBidListIdExistsTest() throws Exception {
        when(bidListService.findBidListById(anyInt())).thenReturn(bidList1);
        doNothing().when(bidListService).deleteBidList(any(BidList.class));
        mockMvc.perform(get("/bidList/delete/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteBid"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser
    void deleteBidAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(bidListService.findBidListById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/bidList/delete/{id}", 1)
                .param("bidListId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteBid"));
    }

    @Test
    @WithAnonymousUser
    void deleteBidUnauthenticatedBidListIdExistsTest() throws Exception {
        mockMvc.perform(get("/bidList/delete/{id}",1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
