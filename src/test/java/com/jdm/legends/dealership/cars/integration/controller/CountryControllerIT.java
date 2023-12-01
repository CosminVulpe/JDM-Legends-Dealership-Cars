package com.jdm.legends.dealership.cars.integration.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.repository.CountryRepository;
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

import java.util.List;

import static com.jdm.legends.dealership.cars.utils.TestDummy.getCountryMock;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.readValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test-in-memory")
@Transactional
public class CountryControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CountryRepository repository;

    private static final String countryRequestMapping = "/country";

    @BeforeEach
    void saveDB() {
        repository.saveAll(getCountryMock());
    }

    @Test
    void getAllCountries() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(countryRequestMapping).accept(APPLICATION_JSON);

        String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        List<String> response = readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(response).isNotEmpty();
    }

    @Test
    void getCountryInfo() throws Exception {
        Country country = repository.findAll().stream().findAny().get();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(countryRequestMapping + "/info/{countryName}", country.getCountryName()).accept(APPLICATION_JSON);

        String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();

        CountryResponse countryResponse = readValue(contentAsString, CountryResponse.class);

        assertThat(countryResponse).isNotNull();
        assertThat(countryResponse.countryName()).isNotNull();
        assertThat(countryResponse.countryName()).isNotNull();
        assertThat(countryResponse.capital()).isNotNull();
    }

}
