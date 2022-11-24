package com.example.demo.service;

import com.example.demo.model.Review;
import com.example.demo.service.DAO.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

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

        log.info(String.format("Review with id %d saved!", review.getId()));
        return ResponseEntity.ok(review);
    }

}
