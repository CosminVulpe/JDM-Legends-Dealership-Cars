package com.jdm.legends.dealership.cars.unit;


import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapper.ReviewMapper;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import com.jdm.legends.dealership.cars.utils.TestDummy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceUnitTest {

    private static final String TITLE = "good";
    private static final String DESCRIPTION = "i love it";
    private static final int STAR_RATING = 5;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldGetAllReviewSuccessfully() {
        List<ReviewDTO> reviewList = List.of(new ReviewDTO(TITLE, DESCRIPTION, STAR_RATING), new ReviewDTO(TITLE, DESCRIPTION, STAR_RATING));
        when(reviewRepository.getRecentReviews()).thenReturn(
                List.of( Review.builder().title(TITLE).starRating(STAR_RATING).description(DESCRIPTION).build()
                        , Review.builder().title(TITLE).starRating(STAR_RATING).description(DESCRIPTION).build()));

        List<ReviewDTO> recentReviews = reviewService.getRecentReviews();
        assertThat(recentReviews).isEqualTo(reviewList);
    }

    @Test
    void shouldAddReviewSuccessfullyIntoDB() {
        ReviewRequest reviewRequest = TestDummy.buildReviewRequest();
        Review review = ReviewMapper.INSTANCE.reviewRequestToReviewEntity(reviewRequest);
        when(reviewRepository.save(any())).thenReturn(review);

        ResponseEntity<ReviewDTO> reviewResponseEntity = reviewService.addReview(reviewRequest);

        verify(reviewRepository).save(review);
        assertThat(reviewResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(reviewResponseEntity.getBody()).isNotNull();
    }

}
