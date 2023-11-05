package com.jdm.legends.dealership.cars.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.dto.TemporaryUser;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jdm.legends.dealership.cars.service.enums.CarColor.BLACK;
import static com.jdm.legends.dealership.cars.service.enums.CarCompany.TOYOTA;
import static com.jdm.legends.dealership.cars.utils.TestData.buildCarRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-in-memory")
@Transactional
class CarControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private HistoryBidRepository historyBidRepository;

    @BeforeEach()
    void insertDB() {
        carRepository.save(buildCarRequest());
    }

    @Test
    void shouldGetAllCarsFromDB() throws Exception {
        MockHttpServletRequestBuilder mock = MockMvcRequestBuilders.get("/car").accept(APPLICATION_JSON);
        mvc.perform(mock).andExpect(status().isOk());

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars.size()).isNotZero();
    }

    @Test
    void shouldGetOneCarById() throws Exception {
        Car car = carRepository.findAll().get(0);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/car/{carId}", car.getId()).accept(APPLICATION_JSON);

        mvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.carName").value("Toyota Supra"))
                .andExpect(jsonPath("$.carColor").value(BLACK.name()))
                .andExpect(jsonPath("$.carCompany").value(TOYOTA.name()))
                .andExpect(jsonPath("$.historyBidList").isNotEmpty())
                .andExpect(jsonPath("$.historyBidList[0].temporaryUsersList").isNotEmpty());

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars.size()).isNotZero();
    }

    @Test
    @Disabled("The test is under development")
    void shouldGetHistoryBidListByCarId() throws Exception {
        Car car = carRepository.findAll().get(0);
        HistoryBid carHistoryBidList = car.getHistoryBidList().get(0);
        TemporaryUser temporaryUser = carHistoryBidList.getTemporaryUsersList().stream().findFirst().orElse(new TemporaryUser());

        carHistoryBidList.setId(car.getId());
        temporaryUser.setId(car.getId());

        historyBidRepository.save(carHistoryBidList);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/car/bid-list/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void getDatesPerCar() throws Exception {
        Car car = carRepository.findAll().get(0);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/car/dates/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<LocalDateTime> datesResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(datesResponse).isNotEmpty();
        assertThat(datesResponse.get(0)).isEqualTo(car.getStartDateCarPostedOnline().toString());
        assertThat(datesResponse.get(1)).isEqualTo(car.getDeadlineCarToSell().toString());
    }

}
