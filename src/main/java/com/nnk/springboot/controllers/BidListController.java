package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidListList", bidListService.findAllBidList());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidList bidList, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            bidListService.saveBidList(bidList);
            model.addAttribute("bidListList",bidListService.findAllBidList());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.findBidListById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.findBidListById(id);
        bidListService.deleteBidList(bidList);
        model.addAttribute("bidListList",bidListService.findAllBidList());
        return "redirect:/bidList/list";
    }
}
