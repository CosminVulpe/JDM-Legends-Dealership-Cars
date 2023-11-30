package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.service.mapper.CountryMapper;
import com.jdm.legends.dealership.cars.service.parserXml.CountryResponse;
import com.jdm.legends.dealership.cars.service.parserXml.Geonames;
import com.jdm.legends.dealership.cars.service.repository.CountryRepo;
import com.jdm.legends.dealership.cars.service.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepo countryRepo;
    private final CountryRepository repository;

    public List<CountryResponse> getCountries() {
       return repository.findAll().stream().map(CountryMapper.INSTANCE::countryEntityToCountryDTO).toList();
    }

    public void saveCountries() {
        List<CountryResponse> countryResponses = parseXmlContent(countryRepo.getCountries()).orElseThrow();
        countryResponses.stream().map(CountryMapper.INSTANCE::countryResponseToCountryEntity).forEachOrdered(repository::save);
    }

    private Optional<List<CountryResponse>> parseXmlContent(String content) {
        try {
            JAXBContext context = JAXBContext.newInstance(Geonames.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader stringReader = new StringReader(content);
            Geonames geonames = (Geonames) unmarshaller.unmarshal(stringReader);

            return Optional.ofNullable(geonames.getCountryList());
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

}
