package com.jdm.legends.dealership.cars.service.mapper;

import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.parserXml.CountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", expression = "java(calculateLatAndLng(countryDTO)[0])")
    @Mapping(target = "longitude", expression = "java(calculateLatAndLng(countryDTO)[1])")
    Country countryDTOToCountryEntity(CountryDTO countryDTO);

    CountryResponse countryEntityToCountryResponse(Country country);

    default double[] calculateLatAndLng(CountryDTO countryDTO) {
        double newLat = countryDTO.getNorth() + countryDTO.getSouth() / 2;
        double newLng = countryDTO.getEast() + countryDTO.getWest() / 2;
        return new double[]{newLat, newLng};
    }

}
