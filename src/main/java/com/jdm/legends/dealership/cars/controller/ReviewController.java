package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.ReviewService;
import com.jdm.legends.dealership.cars.service.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody @Valid ReviewRequest review) {
        return reviewService.addReview(review);
    }

    @GetMapping
    public List<ReviewDTO> getRecentReviews() {
        return reviewService.getRecentReviews();
    }

}
