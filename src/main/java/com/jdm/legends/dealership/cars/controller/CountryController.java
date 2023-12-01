package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import com.jdm.legends.dealership.cars.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @GetMapping
    public List<String> getAllCountriesInfo() {
       return service.getCountries();
    }

    @GetMapping("/info/{countryName}")
    public CountryResponse getAllCountriesInfo(@PathVariable String countryName) {
        return service.getCountryInfo(countryName);
    }

}
