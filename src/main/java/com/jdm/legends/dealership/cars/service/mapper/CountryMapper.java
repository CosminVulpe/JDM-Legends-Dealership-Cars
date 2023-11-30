package com.jdm.legends.dealership.cars.service.mapper;

import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.parserXml.CountryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "id", ignore = true)
    Country countryResponseToCountryEntity(CountryResponse countryResponse);

    CountryResponse countryEntityToCountryDTO(Country country);
}
