package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.CarService.GetCarByIdNotFoundException;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jdm.legends.dealership.cars.utils.UtilsMock.buildCarRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
public class CarServiceIT {

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

}
