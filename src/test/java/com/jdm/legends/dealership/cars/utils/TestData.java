package com.jdm.legends.dealership.cars.utils;

import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.entity.TemporaryCustomer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jdm.legends.dealership.cars.service.enums.CarColor.BLACK;
import static com.jdm.legends.dealership.cars.service.enums.CarCompany.TOYOTA;
import static com.jdm.legends.dealership.cars.service.enums.CarFuelType.GASOLINE;
import static com.jdm.legends.dealership.cars.service.enums.CarTransmissionType.MANUAL_TRANSMISSION;
import static java.time.LocalDateTime.now;

public class TestData {
    private static final String description = "Very good";
    private static final String title = "Recommended to friends";
    private static final int starRating = 5;

    public static ReviewDTO buildReviewRequest() {
        return new ReviewDTO(title, description, starRating);
    }

    public static ReviewDTO buildReviewRequest(String title, String description, int starRating) {
        return new ReviewDTO(title, description, starRating);
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

        TemporaryCustomer temporaryCustomer = TemporaryCustomer.builder()
                .fullName("John Cena")
                .userName("cannot_see_me98")
                .emailAddress("johnCeva12@yahoo.com")
                .role("Potential Client")
                .checkInformationStoredTemporarily(true)
                .build();

        HistoryBid historyBid = HistoryBid.builder()
                .bidValue(new BigDecimal("12354323412"))
                .car(build)
                .timeOfTheBid(now())
                .temporaryUsersList(new HashSet<>(Set.of(temporaryCustomer)))
                .build();

        build.setHistoryBidList(new ArrayList<>(List.of(historyBid)));
        return build;
    }
}
