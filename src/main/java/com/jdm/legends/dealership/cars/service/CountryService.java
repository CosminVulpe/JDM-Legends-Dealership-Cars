package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import com.jdm.legends.dealership.cars.service.CaffeineService.KeyExpiredException;
import com.jdm.legends.dealership.cars.service.mapper.CountryMapper;
import com.jdm.legends.dealership.cars.service.parserXml.CountryDTO;
import com.jdm.legends.dealership.cars.service.parserXml.Geonames;
import com.jdm.legends.dealership.cars.service.repository.CountryRepo;
import com.jdm.legends.dealership.cars.service.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepo countryRepo;
    private final CountryRepository repository;
    private final CaffeineService caffeineService;

    private static final String KEY_CACHING = "oneCountry";

    @Cacheable(value = "countries", key = "'allCountries'")
    public List<String> getCountries() {
        return repository.findAll().stream().map(CountryMapper.INSTANCE::countryEntityToCountryResponse).map(CountryResponse::countryName).toList();
    }

    public CountryResponse getCountryInfo(String countryName) {
        CountryResponse countryResponse = repository.findAll().stream()
                .map(CountryMapper.INSTANCE::countryEntityToCountryResponse)
                .filter(country -> country.countryName().equalsIgnoreCase(countryName))
                .findFirst()
                .orElseThrow(CountryNotFoundException::new);

        caffeineService.saveInCache(KEY_CACHING, countryResponse);
        return countryResponse;
    }

    public String getCountryName() {
        Optional<CountryResponse> countryResponseCache = caffeineService.getCountryResponseCache(KEY_CACHING);

        if (countryResponseCache.isEmpty()) {
            throw new KeyExpiredException("Key-Value stored in Caffeine was unable to be retrieved");
        }

        return countryResponseCache.get().countryName();
    }

    public void saveCountries() {
        List<CountryDTO> countryResponse = parseXmlContent(countryRepo.getCountries());
        countryResponse.stream().map(CountryMapper.INSTANCE::countryDTOToCountryEntity).forEachOrdered(repository::save);
        log.info("Countries saved successfully from external API");
    }

    private List<CountryDTO> parseXmlContent(String content) {
        try {
            JAXBContext context = JAXBContext.newInstance(Geonames.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader stringReader = new StringReader(content);
            Geonames geonames = (Geonames) unmarshaller.unmarshal(stringReader);

            if ( (geonames.getStatus() != null && !geonames.getStatus().getMessage().isBlank())
                    || geonames.getCountryList().isEmpty() ) {
                String msgError = "Country List from external API is empty";
                log.error(msgError);
                throw new IllegalArgumentException(msgError);
            }

            return geonames.getCountryList();
        } catch (JAXBException e) {
            throw new XMLParserException("Parsing the response from API failed", e);
        }
    }

    @Slf4j
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    public static final class XMLParserException extends RuntimeException {
        public XMLParserException(String message, Throwable cause) {
            super(message, cause);
            log.error(message, cause);
        }
    }

    @Slf4j
    @ResponseStatus(code = NOT_FOUND)
    public static final class CountryNotFoundException extends RuntimeException {
        public CountryNotFoundException() {
            super("Country with specific name was not found in the system");
            log.error("Country with specific name was not found in the system");
        }
    }

}
