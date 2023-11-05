package com.jdm.legends.dealership.cars.unit;


import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapping.Mapper;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import com.jdm.legends.dealership.cars.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceUnitTest implements Mapper<ReviewDTO,Review> {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldGetAllReviewSuccessfully() {
        List<Review> reviewList = List.of(new Review(), new Review());
        when(reviewRepository.getRecentReviews()).thenReturn(reviewList);

        List<Review> recentReviews = reviewService.getRecentReviews();
        assertThat(recentReviews).isEqualTo(reviewList);
    }

    @Test
    void shouldAddReviewSuccessfullyIntoDB() {
        ReviewDTO reviewDTO = TestData.buildReviewRequest();
        Review review = map(reviewDTO);
        when(reviewRepository.save(any())).thenReturn(review);

        ResponseEntity<Review> reviewResponseEntity = reviewService.addReview(reviewDTO);

        verify(reviewRepository).save(review);
        assertThat(reviewResponseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Override
    public Review map(ReviewDTO source) {
        return Review.builder()
                .title(source.title())
                .description(source.description())
                .starRating(source.starRating())
                .build();
    }
}
