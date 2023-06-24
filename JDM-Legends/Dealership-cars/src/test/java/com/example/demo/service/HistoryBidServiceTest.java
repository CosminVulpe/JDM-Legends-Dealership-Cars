package com.example.demo.service;


import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryBidServiceTest {

    @Mock
    private HistoryBidRepository repository;

    @Mock
    private CarService carService;

    @InjectMocks
    private HistoryBidService historyBidService;

    @Test
    @Order(1)
    void shouldBidPriceToCar() {
        when(carService.getCarById(any())).thenReturn(new Car());
        HistoryBid historyBid = new HistoryBid();

        historyBidService.bid(123L, historyBid);
        verify(repository).save(historyBid);
    }

}
