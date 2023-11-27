package com.jdm.legends.dealership.cars.integration.controller;

import com.jdm.legends.dealership.cars.service.entity.Car;
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

import static com.jdm.legends.dealership.cars.utils.TestDummy.buildCarRequest;
import static com.jdm.legends.dealership.cars.utils.TestDummy.getHistoryBidTempCustomerMock;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
        Car buildCarRequest = buildCarRequest();
        Car car = carRepository.save(buildCarRequest);

        doNothing().when(temporaryCustomerRepo).saveTempUser(any(),any());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/history-bid/bid/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .content(writeJsonAsString(getHistoryBidTempCustomerMock()))
                .accept(APPLICATION_JSON);

        mvc.perform(builder).andExpect(status().isOk());

        assertThat(historyBidRepository.findAll()).hasSize(1);
    }
}
