package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.AddressRequest;
import com.jdm.legends.dealership.cars.service.entity.Address;
import com.jdm.legends.dealership.cars.service.mapper.AddressMapper;
import com.jdm.legends.dealership.cars.service.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CountryService countryService;

    public ResponseEntity<Void> addAddress(AddressRequest request) {
        Address address = AddressMapper.INSTANCE.addressRequestToAddressEntity(request);
        address.setCountryName(countryService.getCountryName());

        addressRepository.save(address);
        log.info("Address saved successfully");

        return ResponseEntity.status(CREATED).build();
    }

}
