package com.jdm.legends.dealership.cars.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.dealership.cars.service.dto.Review;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jdm.legends.common.enums.CarColor.BLACK;
import static com.jdm.legends.common.enums.CarCompany.TOYOTA;
import static com.jdm.legends.common.enums.CarFuelType.GASOLINE;
import static com.jdm.legends.common.enums.CarTransmissionType.MANUAL_TRANSMISSION;
import static java.time.LocalDateTime.now;

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
