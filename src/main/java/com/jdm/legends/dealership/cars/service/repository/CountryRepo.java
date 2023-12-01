package com.jdm.legends.dealership.cars.service.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
public class CountryRepo {
    private final RestTemplate template;
    private final String geonamesApiUrl;
    private final String username;

    public CountryRepo(RestTemplate template, @Value("${url-geonames-countries-capital}") String geonamesApiUrl, @Value("${username-geonames-countries-capital}") String username) {
        this.template = template;
        this.geonamesApiUrl = geonamesApiUrl;
        this.username = username;
    }

    public String getCountries() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(geonamesApiUrl + "/countryInfo?").queryParam("username", username);
        ResponseEntity<String> responseEntity = template.getForEntity(uriComponentsBuilder.toUriString(), String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful() || isNull(responseEntity.getBody())) {
            throw new GeonamesExternalAPIException();
        }
        return responseEntity.getBody();
    }

//    public String findAddressByPostalCode(String postalCode, String countryCode) {
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(geonamesApiUrl + "/postalCodeSearch?")
//                .queryParam("username", username)
//                .queryParam("postalcode",postalCode)
//                .queryParam("country",countryCode);
//        ResponseEntity<String> responseEntity = template.getForEntity(uriComponentsBuilder.toUriString(), String.class);
//
//        if (!responseEntity.getStatusCode().is2xxSuccessful() || isNull(responseEntity.getBody())) {
//            throw new GeonamesExternalAPIException();
//        }
//        return responseEntity.getBody();
//    }

    @Slf4j
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    public static final class GeonamesExternalAPIException extends RuntimeException {
        public GeonamesExternalAPIException() {
            super("Error while calling external API");
            log.error("Error while calling external API");
        }
    }


}
