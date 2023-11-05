package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.dto.HistoryBidTemporaryUserRequest;
import com.jdm.legends.dealership.cars.service.entity.TemporaryCustomer;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryBidServiceUnitTest {

    private static final String POTENTIAL_CLIENT = "Potential Client";

    @Mock
    private CarService carService;

    @Mock
    private TemporaryCustomerRepo temporaryCustomerRepo;

    @Mock
    private HistoryBidRepository historyBidRepository;

    @InjectMocks
    private HistoryBidService historyBidService;

    @Test
    void bidSuccessfully() {
        when(carService.getCarById(any())).thenReturn(Car.builder().historyBidList(new ArrayList<>()).build());

        historyBidService.bid(1L, buildRequestMockData());

        verify(historyBidRepository).save(any());
        assertThat(buildRequestMockData().getTemporaryUser().getRole()).isEqualTo(POTENTIAL_CLIENT);
        verify(temporaryCustomerRepo).saveTempUser(any());
    }

    private HistoryBidTemporaryUserRequest buildRequestMockData() {
        TemporaryCustomer temporaryCustomer = TemporaryCustomer.builder()
                .emailAddress("test12@gmail.com")
                .checkInformationStoredTemporarily(true)
                .userName("John Cena")
                .role(POTENTIAL_CLIENT)
                .historyBidList(emptyList())
                .build();

        HistoryBid historyBid = HistoryBid.builder()
                .bidValue(new BigDecimal("43522342434"))
                .timeOfTheBid(LocalDateTime.MAX)
                .temporaryUsersList(Set.of(temporaryCustomer))
                .build();
         return HistoryBidTemporaryUserRequest.builder().historyBid(historyBid).temporaryUser(temporaryCustomer).build();
    }

}
