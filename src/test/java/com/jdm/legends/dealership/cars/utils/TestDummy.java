package com.jdm.legends.dealership.cars.utils;

import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jdm.legends.dealership.cars.service.enums.CarColor.BLACK;
import static com.jdm.legends.dealership.cars.service.enums.CarCompany.TOYOTA;
import static com.jdm.legends.dealership.cars.service.enums.CarFuelType.GASOLINE;
import static com.jdm.legends.dealership.cars.service.enums.CarTransmissionType.MANUAL_TRANSMISSION;
import static java.time.LocalDateTime.now;

public class TestDummy {
    private static final String description = "Very good";
    private static final String title = "Recommended to friends";
    private static final int starRating = 5;

    public static ReviewRequest buildReviewRequest() {
        return new ReviewRequest(title, description, starRating);
    }

    public static ReviewRequest buildReviewRequest(String title, String description, int starRating) {
        return new ReviewRequest(title, description, starRating);
    }

    public static Car buildCarRequest() {
        Car toyotaSupra = Car.builder()
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
        toyotaSupra.setHistoryBidList(getHistoryTemporaryCustomerMock(toyotaSupra));

        return toyotaSupra;
    }

    private static List<HistoryBid> getHistoryTemporaryCustomerMock(Car car) {
        return List.of(
                HistoryBid.builder().id(1L).bidValue(new BigDecimal("54234234234")).timeOfTheBid(now().minusDays(2)).temporaryCustomerId(1L).car(car).build(),
                HistoryBid.builder().id(2L).bidValue(new BigDecimal("56524534234")).timeOfTheBid(now().minusDays(5)).temporaryCustomerId(6L).car(car).build(),
                HistoryBid.builder().id(3L).bidValue(new BigDecimal("54975546734")).timeOfTheBid(now().minusDays(10)).temporaryCustomerId(12L).car(car).build()
        );
    }

}
