package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.CustomerRepo;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.jdm.legends.dealership.cars.utils.TestDummy.buildCarRequest;
import static com.jdm.legends.dealership.cars.utils.TestDummy.getHistoryBidTempCustomerMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
class HistoryBidServiceIT {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private HistoryBidService historyBidService;

    @Autowired
    private HistoryBidRepository historyBidRepository;

    @MockBean
    private TemporaryCustomerRepo temporaryCustomerRepo;

    @MockBean
    private CustomerRepo customerRepo;

    @BeforeEach
    void init() {
        carRepository.save(buildCarRequest());
    }

    @Test
    void bidPerCarSuccessfully() {
        Car car = carRepository.findAll().get(0);
        historyBidService.bid(car.getId(), getHistoryBidTempCustomerMock());

        assertThat(historyBidRepository.findAll()).hasSize(1);
        assertThat(historyBidRepository.findAll()).hasAtLeastOneElementOfType(HistoryBid.class);

        verify(temporaryCustomerRepo).saveTempUser(any(), any());
    }

    @Test
    void bidPerCarSuccessfullyCustomer() {
        Car car = carRepository.findAll().get(0);
        doNothing().when(customerRepo).assignCustomerIdToHistoryBid(any(), any());

        historyBidService.bidCustomer(car.getId(), getHistoryBidTempCustomerMock("john@gmail.com").historyBidRequest());

        assertThat(historyBidRepository.findAll()).hasSize(1);
        assertThat(historyBidRepository.findAll()).hasAtLeastOneElementOfType(HistoryBid.class);
   }
}
