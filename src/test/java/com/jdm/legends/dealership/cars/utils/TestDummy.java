package com.jdm.legends.dealership.cars.utils;

import com.jdm.legends.dealership.cars.controller.dto.AddressRequest;
import com.jdm.legends.dealership.cars.controller.dto.HistoryBidRequest;
import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.controller.dto.OrderRequest;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return Stream.of(
                HistoryBid.builder().id(1L).bidValue(new BigDecimal("54234234234")).timeOfTheBid(now().minusDays(2)).temporaryCustomerId(1L).car(car).build(),
                HistoryBid.builder().id(2L).bidValue(new BigDecimal("56524534234")).timeOfTheBid(now().minusDays(5)).temporaryCustomerId(6L).car(car).build(),
                HistoryBid.builder().id(3L).bidValue(new BigDecimal("54975546734")).timeOfTheBid(now().minusDays(10)).temporaryCustomerId(12L).car(car).build()
        ).collect(Collectors.toList());
    }

    public static HistoryBidTemporaryCustomerRequest getHistoryBidTempCustomerMock() {
        HistoryBidRequest historyBidRequest = new HistoryBidRequest(new BigDecimal("23423424"), now().minusHours(5));
        TemporaryCustomerRequest temporaryCustomerRequest = new TemporaryCustomerRequest("John Smith", "john23", "john23@yahoo.com", "ADMIN", true);
        return new HistoryBidTemporaryCustomerRequest(historyBidRequest, temporaryCustomerRequest);
    }

    public static List<Country> getCountryMock() {
        return List.of(
                Country.builder().countryName("United Arab Emirates").isoNumeric("784").capital("Abi Dhabi").continentName("Asia").build(),
                Country.builder().countryName("Bangladesh").isoNumeric("050").capital("Dhaka").continentName("Asia").build(),
                Country.builder().countryName("Indonesia").isoNumeric("360").capital("Jakarta").continentName("Asia").build()
        );
    }

    public static AddressRequest getAddressRequestMock() {
        return new AddressRequest("Maple Avenue", "Riverside", "12345", "California", false);
    }

    public static String getXmlContentCountries() {
        return """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <geonames>
                    <country>
                        <countryCode>AD</countryCode>
                        <countryName>Andorra</countryName>
                        <isoNumeric>020</isoNumeric>
                        <continent>EU</continent>
                        <continentName>Europe</continentName>
                        <capital>Andorra la Vella</capital>
                    </country>
                    <country>
                        <countryCode>AE</countryCode>
                        <countryName>United Arab Emirates</countryName>
                        <isoNumeric>784</isoNumeric>
                        <isoAlpha3>ARE</isoAlpha3>
                        <continent>AS</continent>
                        <continentName>Asia</continentName>
                        <capital>Abu Dhabi</capital>
                    </country>
                </geonames>
                """;
    }

    public static OrderRequest getOrderRequestMock() {
       return new OrderRequest(List.of(getAddressRequestMock()), "+4058203895", "Marine");
    }
}
