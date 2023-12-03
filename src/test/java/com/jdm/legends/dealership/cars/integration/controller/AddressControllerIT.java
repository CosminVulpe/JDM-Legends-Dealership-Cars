package com.jdm.legends.dealership.cars.integration.controller;


import com.jdm.legends.dealership.cars.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static com.jdm.legends.dealership.cars.utils.TestDummy.getAddressRequestMock;
import static com.jdm.legends.dealership.cars.utils.UtilsMock.writeJsonAsString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test-in-memory")
@Transactional
public class AddressControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryService countryService;


    @Test
    void addAddress() throws Exception {
        when(countryService.getCountryName()).thenReturn("United States");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/address/add")
                .accept(APPLICATION_JSON)
                .content(writeJsonAsString(getAddressRequestMock()))
                .contentType(APPLICATION_JSON);

        mvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
