package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BidListServiceTest {

    @Mock
    private static BidListRepository bidListRepository;

    private static BidListService bidListService;

    private BidList bidList1;
    private BidList bidList2;
    private List<BidList> bidListList;

    @BeforeEach
    void beforeEach() {
        bidListService = new BidListServiceImpl(bidListRepository);
        bidListList = new ArrayList<>();
        bidList1 = new BidList("Account 1", "Type 1", 11.00d);
        bidList2 = new BidList("Account 2", "Type 2", 22.00d);
        bidListList.add(bidList1);
        bidListList.add(bidList2);
    }

    // FIND BID LIST BY ID TEST //

    @Test
    void findBidListByIdExistsTest() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList1));
        bidListService.findBidListById(1);
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void findBidListByIdNotExistsTest() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> bidListService.findBidListById(1));
    }

    // FIND ALL BID LIST TEST //

    @Test
    void findAllBidListTest() {
        when(bidListRepository.findAll()).thenReturn(bidListList);
        bidListService.findAllBidList();
        verify(bidListRepository, times(1)).findAll();
    }

    // SAVE BID LIST TEST //

    @Test
    void saveBidListTest() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList1);
        bidListService.saveBidList(bidList1);
        verify(bidListRepository, times(1)).save(bidList1);
    }

    // DELETE BID LIST TEST //

    @Test
    void deleteBidListTest() {
        doNothing().when(bidListRepository).delete(any(BidList.class));
        bidListService.deleteBidList(bidList1);
        verify(bidListRepository, times(1)).delete(bidList1);
    }
}
