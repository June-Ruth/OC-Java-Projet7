package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {

    BidList findBidListById(Integer id);

    List<BidList> findAllBidList();

    BidList saveBidList(BidList bidList);

    BidList updateBidList(BidList bidList);

    void deleteBidList(BidList bidList);

}
