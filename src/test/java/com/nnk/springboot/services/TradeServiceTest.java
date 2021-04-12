package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class TradeServiceTest {

    @Mock
    private static TradeRepository tradeRepository;

    private static TradeService tradeService;

    private Trade trade1;
    private Trade trade2;
    private List<Trade> tradeList;

    @BeforeEach
    void beforeEach() {
        tradeService = new TradeServiceImpl(tradeRepository);
        tradeList = new ArrayList<>();
        trade1 = new Trade("Account 1", "Type 1", 11.00d);
        trade2 = new Trade("Account 2", "Type 2", 22.00d);
        tradeList.add(trade1);
        tradeList.add(trade2);
    }

    // FIND BID LIST BY ID TEST //

    @Test
    void findBidListByIdExistsTest() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade1));
        tradeService.findTradeById(1);
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void findBidListByIdNotExistsTest() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> tradeService.findTradeById(1));
    }

    // FIND ALL BID LIST TEST //

    @Test
    void findAllBidListTest() {
        when(tradeRepository.findAll()).thenReturn(tradeList);
        tradeService.findAllTrade();
        verify(tradeRepository, times(1)).findAll();
    }

    // SAVE BID LIST TEST //

    @Test
    void saveBidListTest() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade1);
        tradeService.saveTrade(trade1);
        verify(tradeRepository, times(1)).save(trade1);
    }

    // DELETE BID LIST TEST //

    @Test
    void deleteBidListTest() {
        doNothing().when(tradeRepository).delete(any(Trade.class));
        tradeService.deleteTrade(trade1);
        verify(tradeRepository, times(1)).delete(trade1);
    }
}
