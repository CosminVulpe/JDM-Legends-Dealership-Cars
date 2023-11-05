package com.jdm.legends.dealership.cars.integration.controller;

import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.dto.HistoryBidTemporaryUserRequest;
import com.jdm.legends.dealership.cars.service.entity.TemporaryCustomer;
import com.jdm.legends.dealership.cars.service.enums.Roles;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
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

import static com.jdm.legends.dealership.cars.utils.TestData.buildCarRequest;
import static com.jdm.legends.dealership.cars.utils.Utils.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test-in-memory")
@Transactional
class HistoryBidControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HistoryBidRepository historyBidRepository;

    @Autowired
    private CarRepository carRepository;

    @MockBean
    private TemporaryCustomerRepo temporaryCustomerRepo;


    @Test
    void testBidSuccessfully() throws Exception {
        Car car = carRepository.save(buildCarRequest());
        HistoryBid historyBid = car.getHistoryBidList().get(0);
        TemporaryCustomer temporaryCustomer = historyBid.getTemporaryUsersList().stream().findFirst().orElse(new TemporaryCustomer());
        HistoryBidTemporaryUserRequest request = HistoryBidTemporaryUserRequest.builder().temporaryUser(temporaryCustomer).historyBid(historyBid).build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/history-bid/bid/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(request))
                .accept(APPLICATION_JSON);

        doNothing().when(temporaryCustomerRepo).saveTempUser(any());

        mvc.perform(builder).andExpect(status().isOk());

        assertThat(car.getHistoryBidList()).hasSize(2);
        assertThat(historyBidRepository.findAll()).hasSize(1);
        assertThat(temporaryCustomer.getEmailAddress()).isEqualTo("johnCeva12@yahoo.com");
        assertThat(temporaryCustomer.getRole()).isEqualTo(Roles.POTENTIAL_CLIENT.getValue());
        verify(temporaryCustomerRepo, atLeastOnce()).saveTempUser(any());
    }
}
