package com.jdm.legends.dealership.cars.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static String writeJsonAsString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while parsing the object", e.getCause());
        }
    }
}
