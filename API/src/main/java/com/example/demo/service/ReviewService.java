package com.example.demo.service;

import com.example.demo.model.Review;
import com.example.demo.service.DAO.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
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
        Review newReview = Review
                .builder()
                .starRating(review.getStarRating())
                .title(review.getTitle())
                .description(review.getDescription())
                .build();
        reviewRepository.save(newReview);

        return ResponseEntity.ok(newReview);
    }

}
