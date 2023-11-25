package com.jdm.legends.dealership.cars.integration.repositories;

import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapping.Mapper;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.jdm.legends.dealership.cars.utils.TestDummy.buildReviewRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
class ReviewRepositoryIT implements Mapper<ReviewRequest,Review> {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void shouldGetRecentReviews() {
        IntStream.range(0, 7).forEach(item-> reviewRepository.save(map(buildReviewRequest())));

        List<Review> recentReviews = reviewRepository.getRecentReviews();
        assertThat(recentReviews).hasSize(5);
    }

    @Override
    public Review map(ReviewRequest source) {
        return Review.builder()
                .description(source.description())
                .title(source.title())
                .starRating(source.starRating())
                .build();
    }
}
