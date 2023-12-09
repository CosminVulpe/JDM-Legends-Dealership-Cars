package com.jdm.legends.dealership.cars.integration.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jdm.legends.dealership.cars.controller.dto.AddressRequest;
import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import com.jdm.legends.dealership.cars.controller.dto.OrderRequest;
import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerDTO;
import com.jdm.legends.dealership.cars.service.CaffeineService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.mapper.CountryMapper;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import com.jdm.legends.dealership.cars.utils.UtilsMock;
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

import java.util.List;

import static com.jdm.legends.dealership.cars.utils.TestDummy.buildCarRequest;
import static com.jdm.legends.dealership.cars.utils.TestDummy.getCountryMock;
import static com.jdm.legends.dealership.cars.utils.TestDummy.getOrderRequestMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-in-memory")
public class OrderControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CaffeineService caffeineService;

    @MockBean
    private TemporaryCustomerRepo temporaryCustomerRepo;

    private final static Cache<String, CountryResponse> cache = Caffeine.newBuilder().build();
    private static final String ONE_COUNTRY_KEY = "oneCountry";

    @BeforeEach()
    void insertDB() {
        carRepository.save(buildCarRequest());
    }

    @Test
    void addOrderSuccessfully() throws Exception {
        Car car = carRepository.findAll().get(0);
        caffeineService.saveInCache(ONE_COUNTRY_KEY, CountryMapper.INSTANCE.countryEntityToCountryResponse(getCountryMock().get(0)));
        when(temporaryCustomerRepo.getWinnerCustomer(any())).thenReturn(new WinnerCustomerDTO(1L, 2L));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/order/add/{carId}", car.getId())
                .accept(APPLICATION_JSON)
                .content(UtilsMock.writeJsonAsString(getOrderRequestMock()))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());

        cache.invalidate(ONE_COUNTRY_KEY);
    }

    @Test
    void shouldAddOrderRequestNotValid() throws Exception {
        Car car = carRepository.findAll().get(0);
        OrderRequest orderRequest = new OrderRequest(
                List.of(new AddressRequest("A", "B", "C", "D", false))
                , "123", "GH");

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/order/add/{carId}", car.getId())
                .accept(APPLICATION_JSON)
                .content(UtilsMock.writeJsonAsString(orderRequest))
                .contentType(APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isBadRequest());
    }
}
