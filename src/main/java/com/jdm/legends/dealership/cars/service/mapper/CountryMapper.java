package com.jdm.legends.dealership.cars.service.mapper;

import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.parserXml.CountryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "id", ignore = true)
    Country countryDTOToCountryEntity(CountryDTO countryDTO);

    CountryDTO countryEntityToCountryDTO(Country country);
}
