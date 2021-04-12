package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TradeController {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(TradeController.class);
    /**
     * @see TradeService
     */
    private TradeService tradeService;

    /**
     * Autowired constructor.
     * @param pTradeService .
     */
    public TradeController(final TradeService pTradeService) {
        tradeService = pTradeService;
    }

    /**
     * Get all the trade.
     * @param model .
     * @return all trade.
     */
    @RequestMapping("/trade/list")
    public String home(final Model model) {
        List<Trade> result = tradeService.findAllTrade();
        model.addAttribute("tradeList", result);
        LOGGER.info("Find all trade, size = " + result.size());
        return "trade/list";
    }

    /**
     * Prepare the form to add a trade.
     * @param trade .
     * @return the form.
     */
    @GetMapping("/trade/add")
    public String addTradeForm(final Trade trade) {
        LOGGER.info("Show form to add trade");
        return "trade/add";
    }

    /**
     * Add a new trade.
     * @param trade to add.
     * @param result .
     * @param model .
     * @return the new list of trade.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid final Trade trade,
                           final BindingResult result,
                           final Model model) {
        LOGGER.info("Try to save new trade : " + trade);
        if (!result.hasErrors()) {
            Trade trade1 = tradeService.saveTrade(trade);
            LOGGER.info("Save trade : " + trade1);
            return "redirect:/trade/list";
        }
        LOGGER.error("Can't save following trade, "
                + "must be invalid data :" + trade);
        return "trade/add";
    }

    /**
     * Get the form to update trade.
     * @param id of the trade to update.
     * @param model .
     * @return form.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        LOGGER.info("Show the form to update trade with id " + id);
        model.addAttribute("trade", tradeService.findTradeById(id));
        return "trade/update";
    }

    /**
     * Update a trade.
     * @param id of the trade to update.
     * @param trade with updated information.
     * @param result .
     * @param model .
     * @return list with updated trade.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer id,
                            @Valid final Trade trade,
                            final BindingResult result,
                            final Model model) {
        LOGGER.info("Try to update trade with id " + id);
        if (!result.hasErrors()) {
            Trade trade1 = tradeService.findTradeById(id);
            trade1.setAccount(trade.getAccount());
            trade1.setType(trade.getType());
            trade1.setBuyQuantity(trade.getBuyQuantity());
            tradeService.saveTrade(trade1);
            LOGGER.info("Succes to update trade " + trade1);
            return "redirect:/trade/list";
        }
        trade.setTradeId(id);
        LOGGER.error("Can't update following trade, "
                + "must be invalid data " + trade);
        return "trade/update";
    }

    /**
     * Delete a trade.
     * @param id of trade to delete.
     * @param model .
     * @return the new list of trade.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer id,
                            final Model model) {
        LOGGER.info("Try to delete trade with id : " + id);
        Trade trade = tradeService.findTradeById(id);
        tradeService.deleteTrade(trade);
        return "redirect:/trade/list";
    }
}
