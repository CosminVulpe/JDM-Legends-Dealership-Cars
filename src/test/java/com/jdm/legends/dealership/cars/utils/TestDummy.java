package com.jdm.legends.dealership.cars.utils;

import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.enums.Roles;

import java.time.LocalDateTime;

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
        return build;
    }

    public static TemporaryCustomerRequest getTempCustomerMock() {
        return new TemporaryCustomerRequest(
                "John Cena"
                , "cannot_see_me98"
                , "johnCeva12@yahoo.com"
                , Roles.POTENTIAL_CLIENT.getValue()
                , true);
    }
}
