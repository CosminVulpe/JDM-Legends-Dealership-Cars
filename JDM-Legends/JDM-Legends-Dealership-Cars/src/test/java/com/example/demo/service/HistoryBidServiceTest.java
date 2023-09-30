package com.example.demo.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.HistoryBidTemporaryUser;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryBidServiceTest {

    private static final String POTENTIAL_CLIENT = "Potential Client";

    @Mock
    private CarService carService;

    @Mock
    private TemporaryUserRepo temporaryUserRepo;

    @Mock
    private HistoryBidRepository historyBidRepository;

    @InjectMocks
    private HistoryBidService historyBidService;

    @Test
    void bidSuccessfully() {
        when(carService.getCarById(any())).thenReturn(Car.builder().historyBidList(new ArrayList<>()).build());

        historyBidService.bid(1L, buildRequestMockData());

        verify(historyBidRepository).save(any());
        assertEquals(POTENTIAL_CLIENT, buildRequestMockData().getTemporaryUser().getRole());
        verify(temporaryUserRepo).saveTempUser(any());
    }

    private HistoryBidTemporaryUser buildRequestMockData() {
        TemporaryUser temporaryUser = TemporaryUser.builder()
                .emailAddress("test12@gmail.com")
                .checkInformationStoredTemporarily(true)
                .userName("John Cena")
                .role(POTENTIAL_CLIENT)
                .historyBidList(emptyList())
                .build();

        HistoryBid historyBid = HistoryBid.builder()
                .bidValue(new BigDecimal("43522342434"))
                .timeOfTheBid(LocalDateTime.MAX)
                .temporaryUsersList(Set.of(temporaryUser))
                .build();
         return HistoryBidTemporaryUser.builder().historyBid(historyBid).temporaryUser(temporaryUser).build();

    }


}
