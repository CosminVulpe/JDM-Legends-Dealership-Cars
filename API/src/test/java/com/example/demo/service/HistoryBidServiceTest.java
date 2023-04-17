package com.example.demo.service;

import com.example.legend.service.CarService;
import com.example.legend.service.HistoryBidService;
import com.example.legend.service.dto.Car;
import com.example.legend.service.dto.HistoryBid;
import com.example.legend.service.repository.HistoryBidRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Test
    @Order(2)
    void shouldGetWinnerWhenPriceIsAvailable() {
        when(carService.getCarById(any()))
                .thenReturn(getCarMockData(getHistoryBidListMockData()));

        Map<String, Object> winnerCarAuction = historyBidService.getWinnerCarAuction(123L);
        assertNotNull(winnerCarAuction.get("CUSTOMER"));
        assertNotNull(winnerCarAuction.get("PRIZE"));
    }

    @Test
    @Order(3)
    void shouldReturnNullWhenNoOneBidAuction() {
        when(carService.getCarById(any()))
                .thenReturn(getCarMockData(Collections.emptyList()));

        Map<String, Object> winnerCarAuction = historyBidService.getWinnerCarAuction(123L);
        assertNull(winnerCarAuction);
    }

    private Car getCarMockData(List<HistoryBid> historyBidList) {
        return Car.builder().historyBidList(historyBidList).build();
    }

    private List<HistoryBid> getHistoryBidListMockData() {
        return List.of(
                HistoryBid.builder().bidValue(20000).build(),
                HistoryBid.builder().bidValue(45671).build(),
                HistoryBid.builder().bidValue(69961).build()
        );
    }

}
