package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.CarService.GetCarByIdNotFoundException;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jdm.legends.dealership.cars.utils.TestData.buildCarRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
class CarServiceIT {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUpDB() {
        carRepository.save(buildCarRequest());
    }

    @Test
    void getAllCars() {
        List<Car> allCars = carService.getAllCars();
        assertThat(allCars).hasSize(1);
    }

    @Test
    void findCarById() {
        Car car = carRepository.findAll().get(0);
        Car carById = carService.getCarById(car.getId());

        assertThat(carById.getCarName()).isEqualTo(car.getCarName());
        assertThat(carById.getKm()).isEqualTo(car.getKm());
        assertThat(carById.getInitialPrice()).isEqualTo(car.getInitialPrice());
        assertThat(carById.getHistoryBidList()).hasSize(1);
    }

    @Test
    void findCarByIdThrowsCustomException() {
        assertThatThrownBy(() -> carService.getCarById(100L))
                .isInstanceOf(GetCarByIdNotFoundException.class)
                .hasMessage("Car with Id provided does not exist");
    }

    @Test
    @Disabled("Test is under development")
    void getBiggestHistoryBidsPerCar() {
    }

    @Test
    void getDatesByCarID() {
        Car car = carRepository.findAll().get(0);
        List<LocalDateTime> datesCar = carService.getDatesCar(car.getId());

        assertThat(datesCar).isNotEmpty();
        assertThat(datesCar.get(0)).isEqualTo(car.getStartDateCarPostedOnline().toString());
        assertThat(datesCar.get(1)).isEqualTo(car.getDeadlineCarToSell().toString());
    }

}