package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.CarService.CarByIdException;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.jdm.legends.dealership.cars.service.enums.CarColor.BLACK;
import static com.jdm.legends.dealership.cars.service.enums.CarCompany.TOYOTA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceUnitTest {
    private static final long CAR_ID = 123L;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void shouldGetAllCarList() {
        List<Car> carListMockData = List.of(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(carListMockData);

        List<Car> allCars = carService.getAllCars();
        assertThat(allCars).isEqualTo(carListMockData);
    }

    @Test
    void shouldGetCarByIdWhenIdExists() {
        Car car = Car.builder().carCompany(TOYOTA).carColor(BLACK).build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));

        Car carById = carService.getCarById(CAR_ID);

        verify(carRepository).findById(any());
        assertThat(car.getCarCompany()).isEqualTo(carById.getCarCompany());
        assertThat(car.getCarColor()).isEqualTo(carById.getCarColor());
    }

    @Test
    void shouldGetCarByIdWhenIdDoesNotExists() {
        when(carRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> carService.getCarById(100L))
                .isInstanceOf(CarByIdException.class)
                .hasMessage("Unable to retrieve a specific car");
    }

    @Test
    void shouldGetDatesByCarId() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 12, 6, 30);
        List<LocalDateTime> actualDate = List.of(localDateTime, localDateTime.plusDays(20));

        Car car = Car
                .builder()
                .startDateCarPostedOnline(localDateTime)
                .deadlineCarToSell(localDateTime.plusDays(20))
                .build();
        Optional<Car> optionalCar = Optional.of(car);

        when(carRepository.findById(any())).thenReturn(optionalCar);

        List<LocalDateTime> datesCar = carService.getDatesCar(CAR_ID);
        assertThat(actualDate).isEqualTo(datesCar);
    }

}
