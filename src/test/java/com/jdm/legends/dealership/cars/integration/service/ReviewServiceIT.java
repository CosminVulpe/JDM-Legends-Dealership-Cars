package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.jdm.legends.dealership.cars.service.mapper.ReviewMapper.INSTANCE;
import static com.jdm.legends.dealership.cars.utils.TestDummy.buildReviewRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
class ReviewServiceIT {

    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewRepository repository;

    @Test
    void getRecentReviewsShouldEqualToFive() {
        List<Review> reviewList = IntStream.range(0, 5).mapToObj(i -> repository.saveAndFlush(INSTANCE.reviewRequestToReviewEntity(buildReviewRequest()))).toList();
        List<ReviewDTO> recentReviews = service.getRecentReviews();

        assertThat(recentReviews.size()).isSameAs(reviewList.size());
        IntStream.range(0, 5).forEach(nr -> {
            assertThat(recentReviews.get(nr).description()).isEqualTo(reviewList.get(nr).getDescription());
            assertThat(recentReviews.get(nr).title()).isEqualTo(reviewList.get(nr).getTitle());
            assertThat(recentReviews.get(nr).starRating()).isEqualTo(reviewList.get(nr).getStarRating());
        });
    }

    @Test
    void addReviewToDBSuccessfully() {
        ReviewRequest reviewRequest = buildReviewRequest();
        Review review = INSTANCE.reviewRequestToReviewEntity(reviewRequest);
        ResponseEntity<ReviewDTO> reviewResponseEntity = service.addReview(reviewRequest);
        ReviewDTO body = reviewResponseEntity.getBody();

        assertThat(reviewResponseEntity.getStatusCode()).isEqualTo(CREATED);
        assertThat(body.description()).isEqualTo(review.getDescription());
        assertThat(body.title()).isEqualTo(review.getTitle());
        assertThat(body.starRating()).isEqualTo(review.getStarRating());
        assertThat(repository.findAll().size()).isNotZero();
    }

}
