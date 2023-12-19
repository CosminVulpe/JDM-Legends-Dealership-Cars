package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.controller.dto.ReminderEmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@Slf4j
public class ReminderEmailRepo {
    private final RestTemplate restTemplate;
    private final String serverHost;
    private final int serverPort;

    public ReminderEmailRepo(RestTemplate restTemplate
            , @Value("${server.host}") String serverHost
            , @Value("${jdm-legends.users.port}") int serverPort) {
        this.restTemplate = restTemplate;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public List<ReminderEmailDTO> getReminderEmails() {

        try {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/reminder-email/all/");
            ResponseEntity<List<ReminderEmailDTO>> responseEntity = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });

            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                String msgError = String.format("Request was not successfully to %s", uriComponentsBuilder.toUriString());
                log.error(msgError);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msgError);
            }

            return responseEntity.getBody();
        } catch (RestClientException e) {
            String msgError = "Error while retrieving all email reminders";
            log.error(msgError);
            throw new RestClientException(msgError);
        }
    }
}
