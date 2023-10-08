package com.jdm.legends.dealership.cars.unit;


import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.dto.Review;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @Order(1)
    void shouldGetAllReviewSuccessfully() {
        List<Review> reviewList = List.of(new Review(), new Review());
        when(reviewRepository.getRecentReviews()).thenReturn(reviewList);

        List<Review> recentReviews = reviewService.getRecentReviews();
        assertEquals(reviewList, recentReviews);
    }

    @Test
    @Order(2)
    void shouldAddReviewSuccessfullyIntoDB() {
        Review review = new Review();
        when(reviewRepository.save(any())).thenReturn(review);

        ResponseEntity<Review> reviewResponseEntity = reviewService.addReview(review);

        verify(reviewRepository).save(review);
        assertThat(reviewResponseEntity.getStatusCodeValue()).isEqualTo(201);
    }

}