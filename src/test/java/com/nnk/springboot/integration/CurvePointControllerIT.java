package com.nnk.springboot.integration;

import com.nnk.springboot.domain.CurvePoint;
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
class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private CurvePoint curvePoint1;
    private CurvePoint curvePoint2;
    private CurvePoint curvePoint3;
    private CurvePoint curvePoint4;

    private List<CurvePoint> curvePointList;

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data-test.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        curvePointList = new ArrayList<>();
        curvePoint1 = new CurvePoint(1, 1.00d, 1.00d);
        curvePoint2 = new CurvePoint(2, 2.00d, 2.00d);
        curvePoint3 = new CurvePoint(3, 3.00d, 3.00d);
        curvePoint4 = new CurvePoint(null, null, 0d);
        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD CURVE POINT FORM TEST //

    @Test
    @WithMockUser
    void addCurvePointFormAuthenticatedIT() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addCurvePointForm"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithAnonymousUser
    void addCurvePointFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("curvePoint", curvePoint3)
                .param("curveId", curvePoint3.getCurveId().toString())
                .param("term", curvePoint3.getTerm().toString())
                .param("value", curvePoint3.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("curvePoint", curvePoint4)
                .param("curveId", "123456.789")
                .param("term", "123456.789")
                .param("value", "123456.789"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(get("/curvePoint/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("curvePoint", curvePoint3)
                .param("curveId", curvePoint3.getCurveId().toString())
                .param("term", curvePoint3.getTerm().toString())
                .param("value", curvePoint3.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedCurvePointIdExistsIT() throws Exception {
        mockMvc.perform(get("/curvePoint/update/{id}", 1)
                .param("curvePointId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedCurvePointIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/curvePoint/update/{id}", 17)
                .param("curvePointId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedIT() throws Exception {
        mockMvc.perform(get("/curvePoint/update/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE CURVE POINT TEST //

    @Test
    @WithMockUser
    void updateCurvePointAuthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("id", "1")
                .param("curveId", curvePoint1.getCurveId().toString())
                .param("term", curvePoint1.getTerm().toString())
                .param("value", curvePoint1.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("updateCurvePoint"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser
    void updateCurvePointAuthenticatedInvalidDataIT() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1")
                .param("curveId", "123456.789")
                .param("term", "123456.789")
                .param("value", "123456.789"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateCurvePoint"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithAnonymousUser
    void updateCurvePointUnauthenticatedValidDataIT() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE CURVE POINT TEST //

    @Test
    @WithMockUser
    void deleteCurvePointAuthenticatedCurvePointIdExistsIT() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteCurvePoint"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser
    void deleteCurvePointAuthenticatedCurvePointIdNotExistsIT() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}", 17)
                .param("curvePointId", "17"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteCurvePoint"));
    }

    @Test
    @WithAnonymousUser
    void deleteCurvePointUnauthenticatedCurvePointIdExistsIT() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}",1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
