package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.service.CountryService;
import com.jdm.legends.dealership.cars.service.parserXml.CountryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @GetMapping
    public List<CountryDTO> getAllCountriesInfo() {
       return service.getCountries();
    }
}
