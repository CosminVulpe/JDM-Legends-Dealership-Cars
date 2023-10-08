package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.dealership.cars.integration.IntegrationTest;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.CarService.GetCarByIdNotFoundException;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.jdm.legends.common.enums.CarColor.BLACK;
import static com.jdm.legends.common.enums.CarCompany.TOYOTA;
import static com.jdm.legends.common.enums.CarFuelType.GASOLINE;
import static com.jdm.legends.common.enums.CarTransmissionType.MANUAL_TRANSMISSION;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class CarServiceTest extends IntegrationTest{

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private HistoryBidRepository historyBidRepository;

    @Autowired
    private TemporaryUserRepo temporaryUserRepo;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUpDB() {
        carRepository.deleteAll();
        carRepository.save(buildCarRequest());
    }

    @Test
    void getAllCars() {
        List<Car> allCars = carService.getAllCars();
        assertThat(allCars.size()).isEqualTo(1);
    }

    @Test
    void findCarById() {
        Car car = carRepository.findAll().get(0);
        Car carById = carService.getCarById(car.getId());

        assertThat(carById.getCarName()).isEqualTo(car.getCarName());
        assertThat(carById.getKm()).isEqualTo(car.getKm());
        assertThat(carById.getInitialPrice()).isEqualTo(car.getInitialPrice());
        assertThat(carById.getHistoryBidList().size()).isEqualTo(1);
    }

    @Test
    void findCarByIdThrowsCustomException() {
        assertThatThrownBy(() -> carService.getCarById(100L))
                .isInstanceOf(GetCarByIdNotFoundException.class)
                .hasMessage("Car with Id provided does not exist");
    }

    @Test
    void getBiggestHistoryBidsPerCar() {
        //TODO
    }

    @Test
    void getDatesByCarID() {
        Car car = carRepository.findAll().get(0);
        List<LocalDateTime> datesCar = carService.getDatesCar(car.getId());

        assertThat(datesCar).isNotEmpty();
        assertThat(datesCar.get(0)).isNotNull();
        assertThat(datesCar.get(1)).isNotNull();
    }

    private Car buildCarRequest() {
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
                .temporaryUsersList(Set.of(temporaryUser))
                .build();

        build.setHistoryBidList(List.of(historyBid));
        return build;
    }

}
