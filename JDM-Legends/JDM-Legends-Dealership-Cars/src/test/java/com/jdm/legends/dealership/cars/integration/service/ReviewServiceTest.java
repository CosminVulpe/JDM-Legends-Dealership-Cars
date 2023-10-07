package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.dto.Review;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewRepository repository;

    private static final String description = "Very good";
    private static final String title = "Recommended to friends";
    private static final int starRating = 5;

    @Test
    @Order(1)
    void getRecentReviewsShouldEqualToFive() {
        List<Review> reviewList = IntStream.range(0, 5).mapToObj(i -> repository.saveAndFlush(getReviewRequest())).toList();
        List<Review> recentReviews = service.getRecentReviews();

        assertThat(recentReviews.size()).isEqualTo(reviewList.size());
        IntStream.range(0, 5).forEach(nr -> {
            assertThat(recentReviews.get(nr).getDescription()).isEqualTo(reviewList.get(nr).getDescription());
            assertThat(recentReviews.get(nr).getTitle()).isEqualTo(reviewList.get(nr).getTitle());
            assertThat(recentReviews.get(nr).getStarRating()).isEqualTo(reviewList.get(nr).getStarRating());
        });
    }

    @Test
    @Order(2)
    void addReviewToDBSuccessfully() {
        Review review = getReviewRequest();

        ResponseEntity<Review> reviewResponseEntity = service.addReview(review);
        Review body = reviewResponseEntity.getBody();

        assertThat(reviewResponseEntity.getStatusCode()).isEqualTo(CREATED);
        assertThat(body.getDescription()).isEqualTo(review.getDescription());
        assertThat(body.getTitle()).isEqualTo(review.getTitle());
        assertThat(body.getStarRating()).isEqualTo(review.getStarRating());

        Review savedReview = repository.findAll().get(0);
        assertThat(savedReview).isEqualTo(review);
        assertThat(repository.findAll().size()).isNotEqualTo(0);
    }

    private Review getReviewRequest() {
        return Review.builder()
                .description(description)
                .title(title)
                .starRating(starRating)
                .build();
    }
}
