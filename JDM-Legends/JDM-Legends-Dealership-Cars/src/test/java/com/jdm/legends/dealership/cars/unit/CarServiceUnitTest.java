package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.enums.CarColor;
import com.jdm.legends.common.enums.CarCompany;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HighestBid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.jdm.legends.common.enums.CarColor.BLACK;
import static com.jdm.legends.common.enums.CarCompany.TOYOTA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        assertThrows(CarService.GetCarByIdNotFoundException.class, () -> carService.getCarById(any()));
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
