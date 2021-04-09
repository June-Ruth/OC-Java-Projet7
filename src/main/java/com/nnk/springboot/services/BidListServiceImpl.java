package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * @see BidListService .
     * @param id .
     * @return bid list
     */
    @Override
    public BidList findBidListById(final Integer id) {
        LOGGER.info("Try to find bid list with id : " + id);
        BidList result = bidListRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No bid list found for id : " + id));
        LOGGER.info("Get bid list with id : " + id + "\n result : " + result);
        return result;
    }

    /**
     * @see BidListService .
     * @return list of bid list.
     */
    @Override
    public List<BidList> findAllBidList() {
        LOGGER.info("Try to get all bid list");
        List<BidList> result = bidListRepository.findAll();
        LOGGER.info("Get all bid list :" + result);
        return result;
    }

    /**
     * @see BidListService .
     * @param bidList to save.
     * @return bid list.
     */
    @Transactional
    @Override
    public BidList saveBidList(final BidList bidList) {
        LOGGER.info("Try to save bid list : " + bidList);
        BidList result = bidListRepository.save(bidList);
        LOGGER.info("Save bid list : " + result);
        return result;
    }

    /**
     * @see BidListService .
     * @param bidList to delete.
     */
    @Transactional
    @Override
    public void deleteBidList(final BidList bidList) {
        LOGGER.info("Try to delete bid list : " + bidList);
        bidListRepository.delete(bidList);
        LOGGER.info("Delete bid list");
    }
}
