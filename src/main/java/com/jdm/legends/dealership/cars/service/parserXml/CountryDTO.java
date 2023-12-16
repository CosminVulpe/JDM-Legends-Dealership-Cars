package com.jdm.legends.dealership.cars.service.parserXml;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CountryDTO {
    private String countryCode;
    private String countryName;
    private String isoNumeric;
    private String continent;
    private String continentName;
    private String capital;
    private String currencyCode;

    private double west;
    private double north;
    private double east;
    private double south;
}
