package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static com.jdm.legends.dealership.cars.utils.TestDummy.getHistoryBidTempCustomerMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryBidServiceUnitTest {

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

        historyBidService.bid(1L, getHistoryBidTempCustomerMock());

        verify(historyBidRepository).save(any());
        verify(temporaryCustomerRepo).saveTempUser(any(), any());
    }

}
