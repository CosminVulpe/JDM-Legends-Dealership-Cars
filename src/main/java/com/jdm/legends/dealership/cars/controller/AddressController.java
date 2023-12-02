package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.AddressRequest;
import com.jdm.legends.dealership.cars.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<Void> addAddress(@RequestBody @Valid AddressRequest request) {
        return addressService.addAddress(request);
    }
}
