package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(TradeServiceImpl.class);
    /**
     * @see TradeRepository
     */
    private final TradeRepository tradeRepository;

    /**
     * Autowired constructor.
     * @param pTradeRepository .
     */
    public TradeServiceImpl(final TradeRepository pTradeRepository) {
        tradeRepository = pTradeRepository;
    }

    /**
     * @see TradeService .
     * @param id .
     * @return trade
     */
    @Override
    public Trade findTradeById(final Integer id) {
        LOGGER.info("Try to find trade with id : " + id);
        Trade result = tradeRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No trade found for id : " + id));
        LOGGER.info("Get trade with id : " + id + "\n result : " + result);
        return result;
    }

    /**
     * @see TradeService .
     * @return list of trade.
     */
    @Override
    public List<Trade> findAllTrade() {
        LOGGER.info("Try to get all trade");
        List<Trade> result = tradeRepository.findAll();
        LOGGER.info("Get all trade, size :" + result.size());
        return result;
    }

    /**
     * @see Trade .
     * @param trade to save.
     * @return trade.
     */
    @Transactional
    @Override
    public Trade saveTrade(final Trade trade) {
        LOGGER.info("Try to save trade : " + trade);
        Trade result = tradeRepository.save(trade);
        LOGGER.info("Save trade : " + result);
        return result;
    }

    /**
     * @see TradeService .
     * @param trade to delete.
     */
    @Transactional
    @Override
    public void deleteTrade(final Trade trade) {
        LOGGER.info("Try to delete trade : " + trade);
        tradeRepository.delete(trade);
        LOGGER.info("Delete trade");
    }
}
