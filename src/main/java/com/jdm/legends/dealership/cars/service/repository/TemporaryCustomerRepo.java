package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class TemporaryCustomerRepo {
    private final RestTemplate restTemplate;
    private final String serverHost;
    private final int serverPort;

    public TemporaryCustomerRepo(RestTemplate restTemplate, @Value("${server.host}") String serverHost, @Value("${jdm-legends.users.port}") int serverPort) {
        this.restTemplate = restTemplate;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void saveTempUser(HistoryBidTemporaryCustomerRequest historyBidTemporaryCustomerRequest) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/temporary-user/save");
        ResponseEntity<HistoryBidTemporaryCustomerRequest> temporaryUserResponseEntity = restTemplate.postForEntity(uriComponentsBuilder.toUriString(), new HttpEntity<>(historyBidTemporaryCustomerRequest), HistoryBidTemporaryCustomerRequest.class);

        if (!temporaryUserResponseEntity.getStatusCode().is2xxSuccessful()) {
            String msgError = "Error while trying to save temporary customer";
            log.warn(msgError);
            throw new RestClientException(msgError);
        }
    }
}
