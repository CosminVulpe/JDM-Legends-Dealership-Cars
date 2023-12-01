package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.service.mapper.CountryMapper;
import com.jdm.legends.dealership.cars.service.parserXml.CountryDTO;
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

    public List<CountryDTO> getCountries() {
        return repository.findAll().stream().map(CountryMapper.INSTANCE::countryEntityToCountryDTO).toList();
    }

    public void saveCountries() {
        List<CountryDTO> countryResponse = parseXmlContent(countryRepo.getCountries()).orElseThrow();
        countryResponse.stream().map(CountryMapper.INSTANCE::countryDTOToCountryEntity).forEachOrdered(repository::save);
    }

    private Optional<List<CountryDTO>> parseXmlContent(String content) {
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

    public static void main(String[] args) {
        double west = 79.6505518289324;
        double north = 9.83586297688552;
        double east = 81.8790900303934;
        double south = 5.91869676126554;

        double[] newCoordinates = calculateCoordinates(north, south, east, west);

        System.out.println("New Latitude: " + newCoordinates[0]);
        System.out.println("New Longitude: " + newCoordinates[1]);
    }

    static double[] calculateCoordinates(double north, double south, double east, double west) {
        double newLat = (north + south) / 2;
        double newLng = (east + west) / 2;
        return new double[]{newLat, newLng};
    }

}
