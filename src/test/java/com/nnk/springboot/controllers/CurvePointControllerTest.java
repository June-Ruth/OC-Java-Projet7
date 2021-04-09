package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.services.CurvePointService;
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

@WebMvcTest(CurvePointController.class)
class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    private CurvePoint curvePoint1;
    private CurvePoint curvePoint2;
    private CurvePoint curvePoint3;
    private CurvePoint curvePoint4;
    private CurvePoint curvePoint5;

    private List<CurvePoint> curvePointList;

    @BeforeEach
    void beforeEach() {
        curvePointList = new ArrayList<>();
        curvePoint1 = new CurvePoint(1, 11.00d, 11.00d);
        curvePoint2 = new CurvePoint(2, 20.00d, 22.00d);
        curvePoint3 = new CurvePoint(3, 30.00d, 33.00d);
        curvePoint4 = new CurvePoint(4, 40.50d, 44.50d);
        curvePoint5 = new CurvePoint(null, null, 0d);
        curvePointList.add(curvePoint1);
        curvePointList.add(curvePoint2);
        curvePointList.add(curvePoint3);
        curvePointList.add(curvePoint4);
    }

    // HOME TEST //

    @Test
    @WithMockUser
    void homeAuthenticatedTest() throws Exception {
        when(curvePointService.findAllCurvePoint()).thenReturn(curvePointList);
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("home"))
                .andExpect(model().attribute("curvePointList", curvePointList))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithAnonymousUser
    void homeUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    // ADD CURVE POINT FORM TEST //

    @Test
    @WithMockUser
    void addCurvePointFormAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("addCurvePointForm"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithAnonymousUser
    void addCurvePointFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // VALIDATE TEST //

    @Test
    @WithMockUser
    void validateAuthenticatedValidDataTest() throws Exception {
        when(curvePointService.saveCurvePoint(any(CurvePoint.class))).thenReturn(curvePoint4);
        mockMvc.perform(post("/curvePoint/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("curvePoint", curvePoint4)
                .param("curveId", curvePoint4.getCurveId().toString())
                .param("term", curvePoint4.getTerm().toString())
                .param("value", curvePoint4.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("validate"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser
    void validateAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("curvePoint", curvePoint5)
                .param("curveId", curvePoint5.getCurveId().toString())
                .param("term", curvePoint5.getTerm().toString())
                .param("value", curvePoint5.getValue().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("validate"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithAnonymousUser
    void validateUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(get("/curvePoint/validate")
                .contentType("text/html;charset=UTF-8")
                .sessionAttr("curvePoint", curvePoint4)
                .param("curveId", curvePoint4.getCurveId().toString())
                .param("term", curvePoint4.getTerm().toString())
                .param("value", curvePoint4.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // SHOW UPDATE FORM TEST //

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedCurvePointIdExistsTest() throws Exception {
        when(curvePointService.findCurvePointById(anyInt())).thenReturn(curvePoint4);
        mockMvc.perform(get("/curvePoint/update/{id}", 4)
                .param("curveId", curvePoint4.getCurveId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("showUpdateForm"))
                .andExpect(request().attribute("curvePoint", curvePoint4))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser
    void showUpdateFormAuthenticatedCurvePointIdNotExistsTest() throws Exception {
        when(curvePointService.findCurvePointById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/curvePoint/update/{id}", 1)
                .param("curvePointId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("showUpdateForm"));
    }

    @Test
    @WithAnonymousUser
    void showUpdateFormUnauthenticatedTest() throws Exception {
        mockMvc.perform(get("/curvePoint/update/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // UPDATE CURVE POINT TEST //

    @Test
    @WithMockUser
    void updateCurvePointAuthenticatedValidDataTest() throws Exception {
        when(curvePointService.findCurvePointById(anyInt())).thenReturn(curvePoint1);
        when(curvePointService.saveCurvePoint(any(CurvePoint.class))).thenReturn(curvePoint1);
        mockMvc.perform(post("/curvePoint/update/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
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
    void updateCurvePointAuthenticatedInvalidDataTest() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1")
                .param("curveId", "")
                .param("term", "")
                .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(handler().methodName("updateCurvePoint"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithAnonymousUser
    void updateCurvePointUnauthenticatedValidDataTest() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    // DELETE CURVE POINT //

    @Test
    @WithMockUser
    void deleteCurvePointAuthenticatedBidListIdExistsTest() throws Exception {
        when(curvePointService.findCurvePointById(anyInt())).thenReturn(curvePoint1);
        doNothing().when(curvePointService).deleteCurvePoint(any(CurvePoint.class));
        mockMvc.perform(get("/bidList/delete/{id}", 1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().methodName("deleteCurvePoint"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser
    void deleteCurvePointAuthenticatedBidListIdNotExistsTest() throws Exception {
        when(curvePointService.findCurvePointById(anyInt())).thenThrow(ElementNotFoundException.class);
        mockMvc.perform(get("/curvePoint/delete/{id}", 1)
                .param("curvePointId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(handler().methodName("deleteCurvePoint"));
    }

    @Test
    @WithAnonymousUser
    void deleteCurvePointUnauthenticatedBidListIdExistsTest() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}",1)
                .sessionAttr("curvePoint", curvePoint1)
                .param("curvePointId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}
