package com.nnk.springboot.controllers;

import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    //TODO : homeTest
    // 1 - if authenticated, then return 200 with BidListList.
    // 2 - if unauthenticated, then redirect to login page.

    //TODO : addBidFormTest
    // 1 - if authenticated, then return 20O with form
    // 2 - if unauthenticated, then redirect to login page

    //TODO : validateTest
    // 1 - if authenticated and data is valid, then save the BidList and return 201
    // 2 - if authenticated and data is invalid, then return ???
    // 3 - if unauthenticated, then redirect to login page

    //TODO : showUpdateFormTest
    // 1 - if authenticated and find BidList by id, then return 200 with form
    // 2 - if authenticated and don't find BidList by id, then return ???
    // 3 - if unauthenticated, then redirect to login page

    //TODO : updateBidTest
    // 1 - if authenticated and data is valid, then update bidList and return 200
    // 2 - if authenticated and data is invalid, then ???
    // 3 - if unauthenticated, then redirect to login page

    //TODO : deleteBidTest
    // 1 - if authenticated and find BidList by id, then delete the bid and return the bidList and 200
    // 2 - if authenticated and don't find BidList by id, then ???
    // 3 - if unauthenticated, then redirect to login page

}
