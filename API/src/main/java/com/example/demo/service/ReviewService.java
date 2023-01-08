package com.example.demo.service;

import com.example.demo.model.Review;
import com.example.demo.service.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public ResponseEntity<List<Review>> getRecentReviews() {
        return (!reviewRepository.getRecentReviews().isEmpty()) ?
                ResponseEntity.ok(reviewRepository.getRecentReviews())
                : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        reviewRepository.save(review);

        log.info("Review with id {} saved", review.getId());
        return new ResponseEntity<>(review, CREATED);
    }

}
