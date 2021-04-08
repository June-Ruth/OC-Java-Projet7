package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {

    List<BidList> findAllBidList();

    BidList saveBidList(BidList bidList);

}
