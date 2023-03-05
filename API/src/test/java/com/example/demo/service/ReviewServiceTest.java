package com.example.demo.service;

import com.example.demo.service.dto.Review;
import com.example.demo.service.repository.ReviewRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
        assertEquals(recentReviews, reviewList);
    }

    @Test
    @Order(2)
    void shouldAddReviewSuccessfullyIntoDB() {
        Review review = new Review();
        when(reviewRepository.save(any())).thenReturn(review);

        ResponseEntity<Review> reviewResponseEntity = reviewService.addReview(review);

        verify(reviewRepository).save(review);
        assertEquals(201, reviewResponseEntity.getStatusCodeValue());
    }

}
