package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.services.TradeService;
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

@WebMvcTest(TradeController.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    private Trade trade1;
    private Trade trade2;
    private Trade trade3;
    private Trade trade4;
    private Trade trade5;

    private List<Trade> tradeList;

    @BeforeEach
    void beforeEach() {
        tradeList = new ArrayList<>();
        trade1 = new Trade("Account 1", "Type 1", 11.00d);
        trade2 = new Trade("Account 2", "Type 2", 22.00d);
        trade3 = new Trade("Account 3", "Type 3", 33.00d);
        trade4 = new Trade("Account 4", "Type 4", 44.50d);
        trade5 = new Trade(null, null, 0d);
        tradeList.add(trade1);
        tradeList.add(trade2);
        tradeList.add(trade3);
        tradeList.add(trade4);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedTest() throws Exception {
        when(tradeService.findAllTrade()).thenReturn(tradeList);
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().attribute("tradeList", tradeList))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD TRADE FORM TEST //

    @Test
    @WithMockUser
    void addTradeFormAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addTradeForm"))
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithAnonymousUser
    void addTradeFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        when(tradeService.saveTrade(any(Trade.class))).thenReturn(trade4);
        mockMvc.perform(post("/trade/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("tradeList", trade4)
                .param("account", trade4.getAccount())
                .param("type", trade4.getType())
                .param("buyQuantity", trade4.getBuyQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("trade", trade5)
                .param("account", trade5.getAccount())
                .param("type", trade5.getType())
                .param("buyQuantity", trade5.getBuyQuantity().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/trade/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("trade", trade4)
                .param("account", trade4.getAccount())
                .param("type", trade4.getType())
                .param("buyQuantity", trade4.getBuyQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdExistsTest() throws Exception {
        when(tradeService.findTradeById(anyInt())).thenReturn(trade1);
        mockMvc.perform(get("/trade/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("trade", trade1))
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(tradeService.findTradeById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/trade/update/{id}", 1)
                .param("tradeId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE TRADE TEST //

    @Test
    @WithMockUser
    void updateTradeAuthenticatedValidDataTest() throws Exception {
        when(tradeService.findTradeById(anyInt())).thenReturn(trade1);
        when(tradeService.saveTrade(any(Trade.class))).thenReturn(trade1);
        mockMvc.perform(post("/bidList/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1")
                .param("account", trade1.getAccount())
                .param("type", trade1.getType())
                .param("buyQuantity", trade1.getBuyQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateTrade"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser
    void updateTradeAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1")
                .param("account", "")
                .param("type", "")
                .param("buyQuantity", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateTrade"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithAnonymousUser
    void updateTradeUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE TRADE TEST //

    @Test
    @WithMockUser
    void deleteTradeAuthenticatedBidListIdExistsTest() throws Exception {
        when(tradeService.findTradeById(anyInt())).thenReturn(trade1);
        doNothing().when(tradeService).deleteTrade(any(Trade.class));
        mockMvc.perform(get("/trade/delete/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteTrade"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser
    void deleteTradeAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(tradeService.findTradeById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/trade/delete/{id}", 1)
                .param("tradeId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteTrade"));
    }

    @Test
    @WithAnonymousUser
    void deleteTradeUnauthenticatedBidListIdExistsTest() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}",1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
