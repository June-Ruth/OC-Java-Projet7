package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.services.RuleNameService;
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

@WebMvcTest(RuleNameController.class)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    private RuleName ruleName1;
    private RuleName ruleName2;
    private RuleName ruleName3;
    private RuleName ruleName4;
    private RuleName ruleName5;

    private List<RuleName> ruleNameList;

    @BeforeEach
    void beforeEach() {
        ruleNameList = new ArrayList<>();
        ruleName1 = new RuleName("name1", "description1", "json1", "template1", "sql1", "sqlPArt1");
        ruleName2 = new RuleName("name2", "description2", "json2", "template2", "sql2", "sqlPArt2");
        ruleName3 = new RuleName("name3", "description3", "json3", "template3", "sql3", "sqlPArt3");
        ruleName4 = new RuleName("name4", "description4", "json4", "template4", "sql4", "sqlPArt4");
        ruleName5 = new RuleName("name5", "................................................................................................................................................................................................................................................................", "json5", "template5", "sql5", "sqlPArt5");
        ruleNameList.add(ruleName1);
        ruleNameList.add(ruleName2);
        ruleNameList.add(ruleName3);
        ruleNameList.add(ruleName4);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedTest() throws Exception {
        when(ruleNameService.findAllRuleName()).thenReturn(ruleNameList);
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().attribute("ruleNameList", ruleNameList))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD RULE NAME FORM TEST //

    @Test
    @WithMockUser
    void addRuleNameFormAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addRuleNameForm"))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithAnonymousUser
    void addRuleNameFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        when(ruleNameService.saveRuleName(any(RuleName.class))).thenReturn(ruleName4);
        mockMvc.perform(post("/ruleName/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("ruleName", ruleName4)
                .param("name", ruleName4.getName())
                .param("description", ruleName4.getDescription())
                .param("json", ruleName4.getJson())
                .param("template", ruleName4.getTemplate())
                .param("sqlStr", ruleName4.getSqlStr())
                .param("sqlPart", ruleName4.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("ruleName", ruleName5)
                .param("name", ruleName5.getName())
                .param("description", ruleName5.getDescription())
                .param("json", ruleName5.getJson())
                .param("template", ruleName5.getTemplate())
                .param("sqlStr", ruleName5.getSqlStr())
                .param("sqlPart", ruleName5.getSqlPart()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/ruleName/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("ruleName", ruleName4)
                .param("name", ruleName4.getName())
                .param("description", ruleName4.getDescription())
                .param("json", ruleName4.getJson())
                .param("template", ruleName4.getTemplate())
                .param("sqlStr", ruleName4.getSqlStr())
                .param("sqlPart", ruleName4.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedRuleNameIdExistsTest() throws Exception {
        when(ruleNameService.findRuleNameById(anyInt())).thenReturn(ruleName4);
        mockMvc.perform(get("/ruleName/update/{id}", 4)
                .param("ruleNameId", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("ruleName", ruleName4))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedRuleNameIdNotExistsTest() throws Exception {
        when(ruleNameService.findRuleNameById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                .param("ruleNameId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/ruleName/update/{id}", 1)
                .sessionAttr("ruleName", ruleName1)
                .param("ruleNameId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE RULE NAME TEST //

    @Test
    @WithMockUser
    void updateRuleNameAuthenticatedValidDataTest() throws Exception {
        when(ruleNameService.findRuleNameById(anyInt())).thenReturn(ruleName1);
        when(ruleNameService.saveRuleName(any(RuleName.class))).thenReturn(ruleName4);
        mockMvc.perform(post("/ruleName/update/{id}", 4)
                .sessionAttr("ruleName", ruleName4)
                .param("name", ruleName4.getName())
                .param("description", ruleName4.getDescription())
                .param("json", ruleName4.getJson())
                .param("template", ruleName4.getTemplate())
                .param("sqlStr", ruleName4.getSqlStr())
                .param("sqlPart", ruleName4.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateRuleName"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser
    void updateRuleNameAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 2)
                .param("name", ruleName5.getName())
                .param("description", ruleName5.getDescription())
                .param("json", ruleName5.getJson())
                .param("template", ruleName5.getTemplate())
                .param("sqlStr", ruleName5.getSqlStr())
                .param("sqlPart", ruleName5.getSqlPart()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateRuleName"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithAnonymousUser
    void updateRuleNameUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE RULE NAME TEST //

    @Test
    @WithMockUser
    void deleteRuleNameAuthenticatedRuleNameIdExistsTest() throws Exception {
        when(ruleNameService.findRuleNameById(anyInt())).thenReturn(ruleName1);
        doNothing().when(ruleNameService).deleteRuleName(any(RuleName.class));
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
    void deleteRuleNameAuthenticatedRuleNameIdNotExistsTest() throws Exception {
        when(ruleNameService.findRuleNameById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/ruleName/delete/{id}", 1)
                .param("ruleNameId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteRuleName"));
    }

    @Test
    @WithAnonymousUser
    void deleteRuleNameUnauthenticatedRuleNameIdExistsTest() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}",1)
                .sessionAttr("ruleName", ruleName1)
                .param("ruleNameId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
