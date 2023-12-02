package com.jdm.legends.dealership.cars.integration.service;


import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import com.jdm.legends.dealership.cars.service.CaffeineService;
import com.jdm.legends.dealership.cars.service.CountryService;
import com.jdm.legends.dealership.cars.service.CountryService.CountryNotFoundException;
import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Sql("/add-countries.sql")
@Transactional
public class CountryServiceIT {
    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CaffeineService caffeineService;

    @Test
    void getAllCountries() {
        List<String> countries = countryService.getCountries();
        assertThat(countries).isNotEmpty();
    }

    @Test
    void getCountryInfo() {
        Country country = countryRepository.findAll().get(0);
        CountryResponse countryInfo = countryService.getCountryInfo(country.getCountryName());

        assertThat(countryInfo).isNotNull();
        assertThat(countryInfo.capital()).isNotNull();
        assertThat(countryInfo.countryName()).isNotNull();
        assertThat(countryInfo.currencyCode()).isNotNull();
        assertThat(countryInfo.countryCode()).isNotNull();
    }

    @Test
    void getCountryInfoShouldThrowExceptionWhenCountryNotFound() {
        assertThatThrownBy(() -> countryService.getCountryInfo("123"))
                .isInstanceOf(CountryNotFoundException.class)
                .hasMessage("Country with specific name was not found in the system");
    }

    @Test
    void getCountryNameCache() {
        String countryName = countryService.getCountryName();
        assertThat(countryName).isNotEmpty();
        assertThat(caffeineService.getCountryResponseCache("oneCountry")).isNotNull();
    }

}
