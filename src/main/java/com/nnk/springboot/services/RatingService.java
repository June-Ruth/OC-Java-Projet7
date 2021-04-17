package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {
    /**
     * Find a rating by its id.
     * @param id .
     * @return rating found - if not found, throws ElementNotFoundException
     * @see Rating
     */
    Rating findRatingById(Integer id);

    /**
     * Find all Rating.
     * @return a list with all rating.
     * @see Rating
     */
    List<Rating> findAllRating();

    /**
     * Save a new rating or update an existing one.
     * @param rating to save.
     * @return rating saved.
     * @see Rating
     */
    Rating saveRating(Rating rating);

    /**
     * Delete an existing rating.
     * @param rating to delete.
     */
    void deleteRating(Rating rating);
}
