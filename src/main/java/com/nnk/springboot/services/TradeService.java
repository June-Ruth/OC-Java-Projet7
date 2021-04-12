package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {
    /**
     * Find a trade by its id.
     * @param id .
     * @return trade found - if not found, throws ElementNotFoundException
     * @see Trade
     */
    Trade findTradeById(Integer id);

    /**
     * Find all trade.
     * @return a list with all trade.
     * @see Trade
     */
    List<Trade> findAllTrade();

    /**
     * Save a new trade or update an existing trade.
     * @param trade to save.
     * @return trade saved.
     * @see Trade
     */
    Trade saveTrade(Trade trade);

    /**
     * Delete an existing trade.
     * @param trade to delete.
     */
    void deleteTrade(Trade trade);
}
