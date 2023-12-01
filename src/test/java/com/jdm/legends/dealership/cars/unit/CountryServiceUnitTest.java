package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.dealership.cars.service.CountryService;
import com.jdm.legends.dealership.cars.service.CountryService.XMLParserException;
import com.jdm.legends.dealership.cars.service.entity.Country;
import com.jdm.legends.dealership.cars.service.parserXml.CountryDTO;
import com.jdm.legends.dealership.cars.service.repository.CountryRepo;
import com.jdm.legends.dealership.cars.service.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryServiceUnitTest {
    @InjectMocks
    private CountryService countryService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryRepo countryRepo;

    @Test
    void saveCountriesSuccessfully() {
        String result = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <geonames>
                    <country>
                        <countryCode>AD</countryCode>
                        <countryName>Andorra</countryName>
                        <isoNumeric>020</isoNumeric>
                        <continent>EU</continent>
                        <continentName>Europe</continentName>
                        <capital>Andorra la Vella</capital>
                    </country>
                    <country>
                        <countryCode>AE</countryCode>
                        <countryName>United Arab Emirates</countryName>
                        <isoNumeric>784</isoNumeric>
                        <isoAlpha3>ARE</isoAlpha3>
                        <continent>AS</continent>
                        <continentName>Asia</continentName>
                        <capital>Abu Dhabi</capital>
                    </country>
                </geonames>
                """;
        when(countryRepo.getCountries()).thenReturn(result);

        countryService.saveCountries();
        verify(countryRepository, times(2)).save(any());
    }

    @Test
    void throwsExceptionWhenXMLIsNotPresent() {
        when(countryRepo.getCountries()).thenReturn("");

        assertThatThrownBy(() ->  countryService.saveCountries())
                .isInstanceOf(XMLParserException.class)
                .hasMessage("Parsing the response from API failed");
    }

    @Test
    void getAllCountries() {
        Country romania = Country.builder()
                .id(1L)
                .countryCode("RO")
                .countryName("Romania")
                .isoNumeric("642")
                .continent("EU")
                .continentName("Europe")
                .capital("Bucharest")
                .currencyCode("RON")
                .build();
        when(countryRepository.findAll()).thenReturn(List.of(romania));

        List<CountryDTO> countries = countryService.getCountries();

        assertThat(countries).isNotEmpty();
    }


}
