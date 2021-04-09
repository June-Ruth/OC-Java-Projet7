package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(BidListServiceImpl.class);
    /**
     * @see RatingRepository
     */
    private final RatingRepository ratingRepository;

    /**
     * Autowired constructor.
     * @param pRatingRepository .
     */
    public RatingServiceImpl(final RatingRepository pRatingRepository) {
        ratingRepository = pRatingRepository;
    }

    /**
     * @see RatingService .
     * @param id .
     * @return rating id
     */
    @Override
    public Rating findRatingById(final Integer id) {
        LOGGER.info("Try to find curve point with id : " + id);
        Rating result = ratingRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(
                        "No rating found for id : " + id));
        LOGGER.info("Get rating with id : " + id + "\n result : " + result);
        return result;
    }

    /**
     * @see RatingService .
     * @return list of rating
     */
    @Override
    public List<Rating> findAllRating() {
        LOGGER.info("Try to get all rating");
        List<Rating> result = ratingRepository.findAll();
        LOGGER.info("Get all rating :" + result);
        return result;
    }

    /**
     * @see RatingService .
     * @param rating to save.
     * @return curve point.
     */
    @Transactional
    @Override
    public Rating saveRating(final Rating rating) {
        LOGGER.info("Try to save rating : " + rating);
        Rating result = ratingRepository.save(rating);
        LOGGER.info("Save rating : " + result);
        return result;
    }

    /**
     * @see RatingService .
     * @param rating to delete.
     */
    @Transactional
    @Override
    public void deleteRating(final Rating rating) {
        LOGGER.info("Try to delete rating : " + rating);
        ratingRepository.delete(rating);
        LOGGER.info("Delete rating");
    }
}
