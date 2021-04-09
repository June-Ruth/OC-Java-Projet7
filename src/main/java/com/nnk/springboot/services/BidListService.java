package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {
    /**
     * Find a bid list by its id.
     * @param id .
     * @return bid list found - if not found, throws ElementNotFoundException
     * @see BidList
     */
    BidList findBidListById(Integer id);

    /**
     * Find all Bid List.
     * @return a list with all bid list.
     * @see BidList
     */
    List<BidList> findAllBidList();

    /**
     * Save a new Bid List or update an existing bid list.
     * @param bidList to save.
     * @return bid list saved.
     * @see BidList
     */
    BidList saveBidList(BidList bidList);

    /**
     * Delete an existing bid list.
     * @param bidList to delete.
     */
    void deleteBidList(BidList bidList);
}
