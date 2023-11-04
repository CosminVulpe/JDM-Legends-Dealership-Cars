package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapping.Mapper;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.jdm.legends.dealership.cars.utils.TestData.buildReviewRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
class ReviewServiceIT implements Mapper<ReviewDTO,Review> {

    @Autowired
    private ReviewService service;

    @Autowired
    private ReviewRepository repository;

    @Test
    void getRecentReviewsShouldEqualToFive() {
        List<Review> reviewList = IntStream.range(0, 5).mapToObj(i -> repository.saveAndFlush(map(buildReviewRequest()))).toList();
        List<Review> recentReviews = service.getRecentReviews();

        assertThat(recentReviews.size()).isSameAs(reviewList.size());
        IntStream.range(0, 5).forEach(nr -> {
            assertThat(recentReviews.get(nr).getDescription()).isEqualTo(reviewList.get(nr).getDescription());
            assertThat(recentReviews.get(nr).getTitle()).isEqualTo(reviewList.get(nr).getTitle());
            assertThat(recentReviews.get(nr).getStarRating()).isEqualTo(reviewList.get(nr).getStarRating());
        });
    }

    @Test
    void addReviewToDBSuccessfully() {
        Review review = map(buildReviewRequest());

        ResponseEntity<Review> reviewResponseEntity = service.addReview(null);
        Review body = reviewResponseEntity.getBody();

        assertThat(reviewResponseEntity.getStatusCode()).isEqualTo(CREATED);
        assertThat(body.getDescription()).isEqualTo(review.getDescription());
        assertThat(body.getTitle()).isEqualTo(review.getTitle());
        assertThat(body.getStarRating()).isEqualTo(review.getStarRating());

        Review savedReview = repository.findAll().get(0);
        assertThat(savedReview).isEqualTo(review);
        assertThat(repository.findAll().size()).isNotZero();
    }

    @Override
    public Review map(ReviewDTO source) {
        return Review.builder()
                .description(source.description())
                .title(source.title())
                .starRating(source.starRating())
                .build();
    }

}
