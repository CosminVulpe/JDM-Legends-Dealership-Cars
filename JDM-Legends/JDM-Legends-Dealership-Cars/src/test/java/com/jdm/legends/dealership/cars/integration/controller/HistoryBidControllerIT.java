package com.jdm.legends.dealership.cars.integration.controller;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.HistoryBidTemporaryUser;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.common.enums.Roles;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.jdm.legends.common.utils.UtilsMock.buildCarRequest;
import static com.jdm.legends.common.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-in-memory")
@Transactional
public class HistoryBidControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HistoryBidRepository historyBidRepository;

    @Autowired
    private CarRepository carRepository;

    @MockBean
    private TemporaryUserRepo temporaryUserRepo;

    @BeforeEach
    void init() {
        carRepository.deleteAll();
    }


    @Test
    void testBidSuccessfully() throws Exception {
        Car car = carRepository.save(buildCarRequest());
        HistoryBid historyBid = car.getHistoryBidList().get(0);
        TemporaryUser temporaryUser = historyBid.getTemporaryUsersList().stream().findFirst().orElse(new TemporaryUser());
        HistoryBidTemporaryUser request = HistoryBidTemporaryUser.builder().temporaryUser(temporaryUser).historyBid(historyBid).build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/history-bid/bid/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(request))
                .accept(APPLICATION_JSON);

        doNothing().when(temporaryUserRepo).saveTempUser(any());

        mvc.perform(builder).andExpect(status().isOk());

        assertThat(car.getHistoryBidList().size()).isEqualTo(2);
        assertThat(historyBidRepository.findAll().size()).isEqualTo(1);
        assertThat(temporaryUser.getEmailAddress()).isEqualTo("johnCeva12@yahoo.com");
        assertThat(temporaryUser.getRole()).isEqualTo(Roles.POTENTIAL_CLIENT.getValue());
        verify(temporaryUserRepo, atLeastOnce()).saveTempUser(any());
    }
}
