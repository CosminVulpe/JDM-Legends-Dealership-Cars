package com.jdm.legends.dealership.cars.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static String writeJsonAsString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while parsing the object", e.getCause());
        }
    }

    public static Car buildCarRequest() {
        Car build = Car.builder()
                .carName("Toyota Supra")
                .carColor(BLACK)
                .carCompany(TOYOTA)
                .carFuelType(GASOLINE)
                .carTransmissionType(MANUAL_TRANSMISSION)
                .damaged(false)
                .initialPrice(333900)
                .km(436522)
                .quantityInStock(4)
                .startDateCarPostedOnline(LocalDateTime.of(2020, 10, 10, 12, 12, 12))
                .deadlineCarToSell(now().plusMonths(3))
                .build();

        TemporaryUser temporaryUser = TemporaryUser.builder()
                .fullName("John Cena")
                .userName("cannot_see_me98")
                .emailAddress("johnCeva12@yahoo.com")
                .role("ADMIN")
                .checkInformationStoredTemporarily(true)
                .build();

        HistoryBid historyBid = HistoryBid.builder()
                .bidValue(new BigDecimal("12354323412"))
                .car(build)
                .timeOfTheBid(now())
                .temporaryUsersList(new HashSet<>(Set.of(temporaryUser)))
                .build();

        build.setHistoryBidList(new ArrayList<>(List.of(historyBid)));
        return build;
    }
}
