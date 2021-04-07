package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(BidListServiceImpl.class);
    /**
     * @see BidListRepository
     */
    private final BidListRepository bidListRepository;

    /**
     * Autowired constructor.
     * @param pBidListRepository .
     */
    public BidListServiceImpl(final BidListRepository pBidListRepository) {
        bidListRepository = pBidListRepository;
    }

    @Override
    public List<BidList> findAllBidList() {
        //TODO
        return bidListRepository.findAll();
    }
}
