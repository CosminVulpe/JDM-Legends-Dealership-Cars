package com.jdm.legends.dealership.cars.utils;

import com.jdm.legends.dealership.cars.service.dto.Review;

public class UtilsMock {
    private static final String description = "Very good";
    private static final String title = "Recommended to friends";
    private static final int starRating = 5;

    public static Review buildReviewRequest() {
        return Review.builder()
                .description(description)
                .title(title)
                .starRating(starRating)
                .build();
    }
}
