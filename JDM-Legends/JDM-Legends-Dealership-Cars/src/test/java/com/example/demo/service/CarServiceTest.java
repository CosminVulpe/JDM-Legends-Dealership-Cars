package com.example.demo.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidInterface;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    private static final long CAR_ID = 123L;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    @Order(1)
    void shouldGetAllCarList() {
        List<Car> carListMockData = List.of(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(carListMockData);

        List<Car> allCars = carService.getAllCars();
        assertEquals(carListMockData, allCars);
    }

    @Test
    @Order(2)
    void shouldGetCarByIdWhenIdExists() {
        Optional<Car> optionalCar = Optional.of(new Car());
        when(carRepository.findById(any())).thenReturn(optionalCar);

        Car carById = carService.getCarById(CAR_ID);

        verify(carRepository, times(1)).findById(any());
        assertEquals(optionalCar.get(), carById);
    }

    @Test
    @Order(3)
    void shouldGetCarByIdWhenIdDoesNotExists() {
        when(carRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CarService.GetCarByIdNotFoundException.class, () -> carService.getCarById(any()));
    }

    @Test
    @Order(4)
    void shouldGetHistoryBidCarByCarId() {
        HistoryBidInterface historyBid1 = new HistoryBidInterface() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public BigDecimal getBidValue() {
                return new BigDecimal("11223344556677");
            }
        };

        HistoryBidInterface historyBid2 = new HistoryBidInterface() {
            @Override
            public Integer getId() {
                return 2;
            }

            @Override
            public BigDecimal getBidValue() {
                return new BigDecimal("7823486.990");
            }
        };

        List<HistoryBidInterface> historyBidInterfaceList = List.of(historyBid1, historyBid2);
        when(carRepository.getBiggestHistoryBidByCarID(any())).thenReturn(historyBidInterfaceList);

        List<HistoryBidInterface> historyBidsList = carService.getHistoryBidsList(CAR_ID);
        assertEquals(historyBidInterfaceList, historyBidsList);
    }

    @Test
    @Order(5)
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

        List<LocalDateTime> datesCar = carService.getDateCar(CAR_ID);
        assertEquals(actualDate, datesCar);
    }

}
