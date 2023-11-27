package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
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

    @Test
    void bidPerCarSuccessfully() {
        Car car = carRepository.save(buildCarRequest());

        historyBidService.bid(car.getId(), getHistoryBidTempCustomerMock());

        assertThat(historyBidRepository.findAll()).hasSize(1);
        assertThat(historyBidRepository.findAll()).isNotEmpty();

        verify(temporaryCustomerRepo).saveTempUser(any(), any());
    }
}
