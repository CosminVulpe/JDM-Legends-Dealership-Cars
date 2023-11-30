package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.entity.Review;
import com.jdm.legends.dealership.cars.service.mapper.ReviewMapper;
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

    public List<ReviewDTO> getRecentReviews() {
        return reviewRepository.getRecentReviews().stream().map(ReviewMapper.INSTANCE::reviewEntityToReviewDTO).toList();
    }

    public ResponseEntity<ReviewDTO> addReview(ReviewRequest request) {
        Review review = ReviewMapper.INSTANCE.reviewRequestToReviewEntity(request);

        reviewRepository.save(review);
        log.info("Review with id {} saved successfully", review.getId());

        ReviewDTO reviewDTO = ReviewMapper.INSTANCE.reviewEntityToReviewDTO(review);
        return new ResponseEntity<>(reviewDTO, CREATED);
    }

}
