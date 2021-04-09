package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.ElementNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class RatingServiceTest {

    @Mock
    private static RatingRepository ratingRepository;

    private static RatingService ratingService;

    private Rating rating1;
    private Rating rating2;

    private List<Rating> ratingList;

    @BeforeEach
    void beforeEach() {
        ratingService = new RatingServiceImpl(ratingRepository);
        ratingList = new ArrayList<>();
        rating1 = new Rating("Moodys1", "SandP1", "Fitch1", 1);
        rating2 = new Rating("Moodys2", "SandP2", "Fitch2", 2);
        ratingList.add(rating1);
        ratingList.add(rating2);
    }

    // FIND RATING BY ID TEST //

    @Test
    void findRatingByIdExistsTest() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating1));
        ratingService.findRatingById(1);
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void findRatingByIdNotExistsTest() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ElementNotFoundException.class, () -> ratingService.findRatingById(1));
    }

    // FIND ALL RATING TEST //

    @Test
    void findAllRatingTest() {
        when(ratingRepository.findAll()).thenReturn(ratingList);
        ratingService.findAllRating();
        verify(ratingRepository, times(1)).findAll();
    }

    // SAVE RATING TEST //

    @Test
    void saveRatingTest() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);
        ratingService.saveRating(rating1);
        verify(ratingRepository, times(1)).save(rating1);
    }

    // DELETE RATING TEST //

    @Test
    void deleteRatingTest() {
        doNothing().when(ratingRepository).delete(any(Rating.class));
        ratingService.deleteRating(rating1);
        verify(ratingRepository, times(1)).delete(rating1);
    }
}
