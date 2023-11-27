package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.CarService.CarByIdException;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jdm.legends.dealership.cars.utils.TestDummy.buildCarRequest;
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

        assertThat(carById).isNotNull();
        assertThat(carById).isEqualTo(car);
    }

    @Test
    void findCarByIdThrowsCustomException() {
        assertThatThrownBy(() -> carService.getCarById(100L))
                .isInstanceOf(CarByIdException.class)
                .hasMessage("Unable to retrieve a specific car");
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
