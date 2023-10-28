package com.jdm.legends.dealership.cars.integration.repositories;

import com.jdm.legends.dealership.cars.service.dto.Review;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.jdm.legends.dealership.cars.utils.TestData.buildReviewRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
class ReviewRepositoryIT {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void shouldGetRecentReviews() {
        IntStream.range(0, 7).forEach(item-> reviewRepository.save(buildReviewRequest()));

        List<Review> recentReviews = reviewRepository.getRecentReviews();
        assertThat(recentReviews).hasSize(5);
    }
}
