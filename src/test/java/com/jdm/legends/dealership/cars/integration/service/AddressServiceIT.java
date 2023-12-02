package com.jdm.legends.dealership.cars.integration.service;

import com.jdm.legends.dealership.cars.service.AddressService;
import com.jdm.legends.dealership.cars.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static com.jdm.legends.dealership.cars.utils.TestDummy.getAddressRequestMock;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test-in-memory")
@Transactional
@Sql("/add-countries.sql")
public class AddressServiceIT {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CountryService countryService;

    @Test
    void addAddressSuccessfully() {
        String country = countryService.getCountries().stream().findAny().get();
        countryService.getCountryInfo(country);

        ResponseEntity<Void> responseEntity = addressService.addAddress(getAddressRequestMock());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
