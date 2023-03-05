package com.example.demo.service;

import com.example.demo.service.dto.Review;
import com.example.demo.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ResponseEntity<Review> addReview(Review review) {
        reviewRepository.save(review);

        log.info("Review with id {} saved", review.getId());
        return new ResponseEntity<>(review, CREATED);
    }

}
