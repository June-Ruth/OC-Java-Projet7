package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(BidListController.class);
    /**
     * @see BidListService
     */
    private BidListService bidListService;

    /**
     * Autowired constructor.
     * @param pBidListService .
     */
    public BidListController(final BidListService pBidListService) {
        bidListService = pBidListService;
    }

    /**
     * Get all the bid list.
     * @param model .
     * @return all bid list.
     */
    @RequestMapping("/bidList/list")
    public String home(final Model model) {
        List<BidList> result = bidListService.findAllBidList();
        model.addAttribute("bidListList", result);
        LOGGER.info("Find all bid list, size = " + result.size());
        return "bidList/list";
    }

    /**
     * Prepare the form to add a bid list.
     * @param bidList .
     * @return the form.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(final BidList bidList) {
        LOGGER.info("Show form to add bid list");
        return "bidList/add";
    }

    /**
     * Add a new bid list.
     * @param bidList to add.
     * @param result .
     * @param model .
     * @return the new list of bid list.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid final BidList bidList,
                           final BindingResult result,
                           final Model model) {
        LOGGER.info("Try to save new bid list : " + bidList);
        if (!result.hasErrors()) {
            BidList bidList1 = bidListService.saveBidList(bidList);
            LOGGER.info("Save bid list : " + bidList1);
            return "redirect:/bidList/list";
        }
        LOGGER.error("Can't save following bid list, "
                + "must be invalid data :" + bidList);
        return "bidList/add";
    }

    /**
     * Get the form to update bid list.
     * @param id of the bid list to update.
     * @param model .
     * @return form.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        LOGGER.info("Show the form to update bid list with id " + id);
        model.addAttribute("bidList", bidListService.findBidListById(id));
        return "bidList/update";
    }

    /**
     * Update a bid list.
     * @param id of the bid list to update.
     * @param bidList with updated information.
     * @param result .
     * @param model .
     * @return list with updated bid list.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id,
                            @Valid final BidList bidList,
                            final BindingResult result,
                            final Model model) {
        LOGGER.info("Try to update bid list with id " + id);
        if (!result.hasErrors()) {
            BidList bidList1 = bidListService.findBidListById(id);
            bidList1.setAccount(bidList.getAccount());
            bidList1.setType(bidList.getType());
            bidList1.setBidQuantity(bidList.getBidQuantity());
            bidListService.saveBidList(bidList1);
            LOGGER.info("Succes to update bid list " + bidList1);
            return "redirect:/bidList/list";
        }
        bidList.setBidListId(id);
        LOGGER.error("Can't update following bid list, "
                + "must be invalid data " + bidList);
        return "bidList/update";
    }

    /**
     * Delete a bid list.
     * @param id of bid list to delete.
     * @param model .
     * @return the new list of bid list.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id,
                            final Model model) {
        LOGGER.info("Try to delete bid list with id : " + id);
        BidList bidList = bidListService.findBidListById(id);
        bidListService.deleteBidList(bidList);
        return "redirect:/bidList/list";
    }
}
