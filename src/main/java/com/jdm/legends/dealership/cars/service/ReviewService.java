package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapping.Mapper;
import com.jdm.legends.dealership.cars.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getRecentReviews() {
        return reviewRepository.getRecentReviews();
    }

    public ResponseEntity<Review> addReview(ReviewRequest request) {
        Mapper<ReviewRequest, Review> mapper = (ReviewRequest source) ->
                Review.builder()
                        .title(source.title())
                        .description(source.description())
                        .starRating(source.starRating())
                        .build();

        Review review = mapper.map(request);
        reviewRepository.save(review);

        log.info("Review with id {} saved", review.getId());
        return new ResponseEntity<>(review, CREATED);
    }

}
