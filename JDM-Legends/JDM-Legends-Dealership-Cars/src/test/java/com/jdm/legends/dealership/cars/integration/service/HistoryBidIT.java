package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.HistoryBidTemporaryUser;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.common.enums.Roles;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.jdm.legends.common.utils.UtilsMock.buildCarRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
public class HistoryBidIT {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private HistoryBidService historyBidService;

    @Autowired
    private HistoryBidRepository historyBidRepository;

    @MockBean
    private TemporaryUserRepo temporaryUserRepo;

    @BeforeEach
    void setUpDB() {
        carRepository.deleteAll();
        carRepository.save(buildCarRequest());
    }

    @Test
    void bidPerCarSuccessfully() {
        Car car = carRepository.findAll().get(0);
        HistoryBid historyBid = car.getHistoryBidList().get(0);
        TemporaryUser temporaryUser = historyBid.getTemporaryUsersList().stream().findFirst().get();

        HistoryBidTemporaryUser build = HistoryBidTemporaryUser.builder()
                .historyBid(historyBid)
                .temporaryUser(temporaryUser)
                .build();
        doNothing().when(temporaryUserRepo).saveTempUser(any());

        historyBidService.bid(car.getId(), build);

        assertThat(car.getHistoryBidList().size()).isEqualTo(2);
        assertThat(historyBidRepository.findAll()).contains(historyBid);
        assertThat(temporaryUser.getRole()).isEqualTo(Roles.POTENTIAL_CLIENT.getValue());
    }
}
