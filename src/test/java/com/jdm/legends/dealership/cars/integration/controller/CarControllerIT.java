package com.jdm.legends.dealership.cars.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerResponse;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.jdm.legends.dealership.cars.utils.TestDummy.buildCarRequest;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.readValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private static final String carRequestMapping = "/car";

    @BeforeEach()
    void insertDB() {
        carRepository.save(buildCarRequest());
    }

    @Test
    void shouldGetAllCarsFromDB() throws Exception {
        MockHttpServletRequestBuilder mock = MockMvcRequestBuilders.get(carRequestMapping).accept(APPLICATION_JSON);
        String contentAsString = mvc.perform(mock).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<Car> carList = readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(carList).isNotEmpty();
        carList.forEach(car -> assertThat(car).isNotNull());
    }

    @Test
    void shouldGetOneCarById() throws Exception {
        Car car = carRepository.findAll().get(0);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(carRequestMapping + "/{carId}", car.getId()).accept(APPLICATION_JSON);
        String contentAsString = mvc.perform(builder)
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Car response = readValue(contentAsString, Car.class);

        assertThat(response).isNotNull();
        assertThat(response.getHistoryBidList()).isNotEmpty();
    }

    @Test
    void shouldThrowExceptionWhenGetOneCarIdIsNotPresent() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(carRequestMapping + "/{carId}", 100L).accept(APPLICATION_JSON);
        mvc.perform(builder).andExpect(status().isNotFound());
    }

    @Test
    void getDatesPerCar() throws Exception {
        Car car = carRepository.findAll().get(0);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(carRequestMapping + "/dates/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        String contentAsString = mvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<LocalDateTime> datesResponse = readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(datesResponse).isNotEmpty();
        assertThat(datesResponse.get(0)).isEqualTo(car.getStartDateCarPostedOnline().toString());
        assertThat(datesResponse.get(1)).isEqualTo(car.getDeadlineCarToSell().toString());
    }

    @Test
    void getWinnerSuccessfully() throws Exception {
        Car car = carRepository.findAll().get(0);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(carRequestMapping + "/max/bidValue/{carId}", car.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        String contentAsString = mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        WinnerCustomerResponse winnerCustomerResponse = readValue(contentAsString, WinnerCustomerResponse.class);

       assertThat(winnerCustomerResponse).isNotNull();
       assertThat(winnerCustomerResponse.bidValue()).isNotNull();
       assertThat(winnerCustomerResponse.historyBidId()).isNotNull();
    }

}
