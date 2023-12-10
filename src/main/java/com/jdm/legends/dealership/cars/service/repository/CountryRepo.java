package com.jdm.legends.dealership.cars.service.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
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

    public CountryRepo(RestTemplate template
            , @Value("${url-geonames-countries-capital}") String geonamesApiUrl
            , @Value("${username-geonames-countries-capital}") String username) {
        this.template = template;
        this.geonamesApiUrl = geonamesApiUrl;
        this.username = username;
    }

    @Retryable(value = {RestClientException.class}, backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public String getCountries() {
        try {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(geonamesApiUrl + "/countryInfo?").queryParam("username", username);
            ResponseEntity<String> responseEntity = template.getForEntity(uriComponentsBuilder.toUriString(), String.class);

            log.info("Response from geonames API with status {}", responseEntity.getStatusCode());
            if (!responseEntity.getStatusCode().is2xxSuccessful() || isNull(responseEntity.getBody())) {
                throw new GeonamesExternalAPIException();
            }

            return responseEntity.getBody();
        } catch (RestClientException e) {
            String msgError = "Error while calling external API";
            log.error(msgError, e);
            throw new RestClientException(msgError, e);
        }
    }

    @Slf4j
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    public static final class GeonamesExternalAPIException extends RuntimeException {
        public GeonamesExternalAPIException() {
            super("API call was unable to pass through");
            log.error("API call was unable to pass through");
        }
    }

}
