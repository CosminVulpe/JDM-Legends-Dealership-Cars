package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.AddressRequest;
import com.jdm.legends.dealership.cars.service.entity.Address;
import com.jdm.legends.dealership.cars.service.mapper.AddressMapper;
import com.jdm.legends.dealership.cars.service.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CountryService countryService;

    public Address addAddress(AddressRequest request) {
        Address address = AddressMapper.INSTANCE.addressRequestToAddressEntity(request);
        address.setCountryName(countryService.getCountryName());

        Address addressSaved = addressRepository.save(address);
        log.info("Address saved successfully");

        return addressSaved;
    }

}
