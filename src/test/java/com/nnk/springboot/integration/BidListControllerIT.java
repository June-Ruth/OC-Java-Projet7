package com.nnk.springboot.integration;

import com.nnk.springboot.domain.BidList;
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
class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private BidList bidList1;
    private BidList bidList2;
    private BidList bidList3;
    private BidList bidList4;

    private List<BidList> bidListList;

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-test.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bidList1 = new BidList("Account 1", "Type 1", 10.00d);
        bidList1.setBidListId(1);
        bidList2 = new BidList("Account 2", "Type 2", 20.00d);
        bidList2.setBidListId(2);
        bidList3 = new BidList("Account 3", "Type 3", 30.00d);
        bidList4 = new BidList(null, null, 0.00d);
        bidListList = new ArrayList<>();
        bidListList.add(bidList1);
        bidListList.add(bidList2);

    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD BID FORM TEST //

    @Test
    @WithMockUser
    void addBidFormAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addBidForm"))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithAnonymousUser
    void addBidFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("bidList", bidList3)
                .param("account", bidList3.getAccount())
                .param("type", bidList3.getType())
                .param("bidQuantity", bidList3.getBidQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("bidList", bidList4)
                .param("account", bidList4.getAccount())
                .param("type", bidList4.getType())
                .param("bidQuantity", bidList4.getBidQuantity().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(get("/bidList/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("bidList", bidList3)
                .param("account", bidList3.getAccount())
                .param("type", bidList3.getType())
                .param("bidQuantity", bidList3.getBidQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdExistsIT() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedBidListIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 17)
                .param("bidListId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE BID TEST //

    @Test
    @WithMockUser
    void updateBidAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1")
                .param("account", bidList3.getAccount())
                .param("type", bidList1.getType())
                .param("bidQuantity", bidList1.getBidQuantity().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateBid"))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser
    void updateBidAuthenticatedInvalidDataIT() throws Exception {
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
    void updateBidUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE BID TEST //

    @Test
    @WithMockUser
    void deleteBidAuthenticatedBidListIdExistsIT() throws Exception {
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
    void deleteBidAuthenticatedBidListIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/bidList/delete/{id}", 17)
                .param("bidListId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteBid"));
    }

    @Test
    @WithAnonymousUser
    void deleteBidUnauthenticatedBidListIdExistsIT() throws Exception {
        mockMvc.perform(get("/bidList/delete/{id}",1)
                .sessionAttr("bidList", bidList1)
                .param("bidListId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
