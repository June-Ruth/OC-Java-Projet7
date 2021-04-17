package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Trade;
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
class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private Trade trade1;
    private Trade trade2;
    private Trade trade3;
    private Trade trade4;

    private List<Trade> tradeList;

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-test.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tradeList = new ArrayList<>();
        trade1 = new Trade("Account 1", "Type 1", 11.00d);
        trade2 = new Trade("Account 2", "Type 2", 22.00d);
        trade3 = new Trade("Account 3", "Type 3", 33.00d);
        trade4 = new Trade(null, null, 0d);
        tradeList.add(trade1);
        tradeList.add(trade2);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD TRADE FORM TEST //

    @Test
    @WithMockUser
    void addTradeFormAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addTradeForm"))
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithAnonymousUser
    void addTradeFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("tradeList", trade3)
                .param("account", trade3.getAccount())
                .param("type", trade3.getType())
                .param("buyQuantity", trade3.getBuyQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("trade", trade4)
                .param("account", trade4.getAccount())
                .param("type", trade4.getType())
                .param("buyQuantity", trade4.getBuyQuantity().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(get("/trade/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("trade", trade3)
                .param("account", trade3.getAccount())
                .param("type", trade3.getType())
                .param("buyQuantity", trade3.getBuyQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedTradeIdExistsIT() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedTradeIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 17)
                .param("tradeId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE TRADE TEST //

    @Test
    @WithMockUser
    void updateTradeAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1")
                .param("account", trade3.getAccount())
                .param("type", trade1.getType())
                .param("buyQuantity", trade1.getBuyQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateTrade"))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser
    void updateTradeAuthenticatedInvalidDataIT() throws Exception {
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
    void updateTradeUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE TRADE TEST //

    @Test
    @WithMockUser
    void deleteTradeAuthenticatedBidListIdExistsIT() throws Exception {
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
    void deleteTradeAuthenticatedBidListIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", 17)
                .param("tradeId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteTrade"));
    }

    @Test
    @WithAnonymousUser
    void deleteTradeUnauthenticatedBidListIdExistsIT() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}",1)
                .sessionAttr("trade", trade1)
                .param("tradeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
