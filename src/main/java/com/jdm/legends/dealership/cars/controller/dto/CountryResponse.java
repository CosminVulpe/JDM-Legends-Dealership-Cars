package com.jdm.legends.dealership.cars.controller.dto;

public record CountryResponse(
        String countryCode,
        String countryName,
        String continent,
        String continentName,
        String capital,
        String currencyCode,
        double latitude,
        double longitude
) {
}
