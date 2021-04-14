package com.nnk.springboot.integration;

import com.nnk.springboot.domain.RuleName;
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
class RuleNameControllerIT {


    @Autowired
    private MockMvc mockMvc;

    private RuleName ruleName1;
    private RuleName ruleName2;
    private RuleName ruleName3;
    private RuleName ruleName4;

    private List<RuleName> ruleNameList;

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-test.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ruleName1 = new RuleName("Name 1", "Description 1", "Json 1", "Template 1", "SQL str 1", "SQL Part 1");
        ruleName2 = new RuleName("Name 2", "Description 2", "Json 2", "Template 2", "SQL str 2", "SQL Part 2");
        ruleName3 = new RuleName("name4", "description4", "json4", "template4", "sql4", "sqlPArt4");
        ruleName4 = new RuleName("name5", "................................................................................................................................................................................................................................................................", "json5", "template5", "sql5", "sqlPArt5");
        ruleNameList = new ArrayList<>();
        ruleNameList.add(ruleName1);
        ruleNameList.add(ruleName2);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD RULE NAME FORM TEST //

    @Test
    @WithMockUser
    void addRuleNameFormAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addRuleNameForm"))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithAnonymousUser
    void addRuleNameFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("ruleName", ruleName3)
                .param("name", ruleName3.getName())
                .param("description", ruleName3.getDescription())
                .param("json", ruleName3.getJson())
                .param("template", ruleName3.getTemplate())
                .param("sqlStr", ruleName3.getSqlStr())
                .param("sqlPart", ruleName3.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("ruleName", ruleName4)
                .param("name", ruleName4.getName())
                .param("description", ruleName4.getDescription())
                .param("json", ruleName4.getJson())
                .param("template", ruleName4.getTemplate())
                .param("sqlStr", ruleName4.getSqlStr())
                .param("sqlPart", ruleName4.getSqlPart()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(get("/ruleName/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("ruleName", ruleName3)
                .param("name", ruleName3.getName())
                .param("description", ruleName3.getDescription())
                .param("json", ruleName3.getJson())
                .param("template", ruleName3.getTemplate())
                .param("sqlStr", ruleName3.getSqlStr())
                .param("sqlPart", ruleName3.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedRuleNameIdExistsIT() throws Exception {
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                .param("ruleNameId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedRuleNameIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/ruleName/update/{id}", 17)
                .param("ruleNameId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                .sessionAttr("ruleName", ruleName1)
                .param("ruleNameId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE RULE NAME TEST //

    @Test
    @WithMockUser
    void updateRuleNameAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 1)
                .sessionAttr("ruleName", ruleName1)
                .param("name", ruleName3.getName())
                .param("description", ruleName3.getDescription())
                .param("json", ruleName3.getJson())
                .param("template", ruleName3.getTemplate())
                .param("sqlStr", ruleName3.getSqlStr())
                .param("sqlPart", ruleName3.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateRuleName"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser
    void updateRuleNameAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 1)
                .param("name", ruleName4.getName())
                .param("description", ruleName4.getDescription())
                .param("json", ruleName4.getJson())
                .param("template", ruleName4.getTemplate())
                .param("sqlStr", ruleName4.getSqlStr())
                .param("sqlPart", ruleName4.getSqlPart()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateRuleName"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithAnonymousUser
    void updateRuleNameUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE RULE NAME TEST //

    @Test
    @WithMockUser
    void deleteRuleNameAuthenticatedRuleNameIdExistsIT() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}", 1)
                .sessionAttr("ruleName", ruleName1)
                .param("ruleNameId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteRuleName"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser
    void deleteRuleNameAuthenticatedRuleNameIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}", 17)
                .param("ruleNameId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteRuleName"));
    }

    @Test
    @WithAnonymousUser
    void deleteRuleNameUnauthenticatedRuleNameIdExistsIT() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}",1)
                .sessionAttr("ruleName", ruleName1)
                .param("ruleNameId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
