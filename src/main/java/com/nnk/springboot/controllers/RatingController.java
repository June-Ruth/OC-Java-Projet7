package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(RatingController.class);
    /**
     * @see RatingService
     */
    private RatingService ratingService;

    /**
     * Autowired constructor.
     * @param pRatingService .
     */
    public RatingController(final RatingService pRatingService) {
        ratingService = pRatingService;
    }

    /**
     * Get all the rating.
     * @param model .
     * @return all rating.
     */
    @RequestMapping("/rating/list")
    public String home(final Model model) {
        List<Rating> result = ratingService.findAllRating();
        model.addAttribute("ratingList", result);
        LOGGER.info("Find all rating, size = " + result.size());
        return "rating/list";
    }

    /**
     * Prepare the form to add a rating.
     * @param rating .
     * @return the form.
     */
    @GetMapping("/curvePoint/add")
    public String addRatingForm(final Rating rating) {
        LOGGER.info("Show form to add rating");
        return "rating/add";
    }

    /**
     * Add a new rating.
     * @param rating to add.
     * @param result .
     * @param model .
     * @return the new list of rating.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid final Rating rating,
                           final BindingResult result,
                           final Model model) {
        LOGGER.info("Try to save new rating : " + rating);
        if (!result.hasErrors()) {
            Rating rating1 = ratingService.saveRating(rating);
            LOGGER.info("Save curve point : " + rating1);
            return "redirect:/rating/list";
        }
        LOGGER.error("Can't save following rating, "
                + "must be invalid data :" + rating);
        return "rating/add";
    }

    /**
     * Get the form to update rating.
     * @param id of the rating to update.
     * @param model .
     * @return form.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        LOGGER.info("Show the form to update rating with id " + id);
        model.addAttribute("rating", ratingService.findRatingById(id));
        return "rating/update";
    }

    /**
     * Update a rating.
     * @param id of the rating to update.
     * @param rating with updated information.
     * @param result .
     * @param model .
     * @return list with updated rating.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") final Integer id,
                                   @Valid final Rating rating,
                                   final BindingResult result,
                                   final Model model) {
        LOGGER.info("Try to update rating with id " + id);
        if (!result.hasErrors()) {
            Rating rating1 = ratingService.findRatingById(id);
            rating1.setMoodysRating(rating.getMoodysRating());
            rating1.setSandPRating(rating.getSandPRating());
            rating1.setFitchRating(rating.getFitchRating());
            rating1.setOrderNumber(rating.getOrderNumber());
            LOGGER.info("Succes to update rating " + rating1);
            return "redirect:/rating/list";
        }
        rating.setId(id);
        LOGGER.error("Can't update following rating, "
                + "must be invalid data " + rating);
        return "rating/update";
    }

    /**
     * Delete a rating.
     * @param id of rating to delete.
     * @param model .
     * @return the new list of rating.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer id,
                                   final Model model) {
        LOGGER.info("Try to delete rating with id : " + id);
        Rating rating = ratingService.findRatingById(id);
        ratingService.deleteRating(rating);
        return "redirect:/rating/list";
    }
}
