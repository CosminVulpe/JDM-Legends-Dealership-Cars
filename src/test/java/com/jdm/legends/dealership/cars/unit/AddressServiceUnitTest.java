package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.dealership.cars.service.AddressService;
import com.jdm.legends.dealership.cars.service.CountryService;
import com.jdm.legends.dealership.cars.service.entity.Address;
import com.jdm.legends.dealership.cars.service.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static com.jdm.legends.dealership.cars.utils.TestDummy.getAddressRequestMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceUnitTest {

    @Mock
    private AddressRepository repository;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private AddressService addressService;

    @Test
    void addAddress() {
        when(countryService.getCountryName()).thenReturn("United States");
        when(repository.save(any())).thenReturn(new Address());

        Address address = addressService.addAddress(getAddressRequestMock());

        assertThat(address).isNotNull();
        verify(repository).save(any());
    }

}
