package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerResponse;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class TemporaryCustomerRepo {
    private final RestTemplate restTemplate;
    private final String serverHost;
    private final int serverPort;
    private final HistoryBidRepository repository;

    public TemporaryCustomerRepo(RestTemplate restTemplate, @Value("${server.host}") String serverHost, @Value("${jdm-legends.users.port}") int serverPort, HistoryBidRepository repository) {
        this.restTemplate = restTemplate;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.repository = repository;
    }

    public void saveTempUser(TemporaryCustomerRequest temporaryCustomerRequest, HistoryBid historyBidSaved) {
        Long id = historyBidSaved.getId();
        UriComponents request = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/temporary-customer/save/{historyBidId}").buildAndExpand(id);
        TemporaryCustomerResponse temporaryCustomerResponse = restTemplate.postForObject(request.toUriString(), new HttpEntity<>(temporaryCustomerRequest), TemporaryCustomerResponse.class);

        if (temporaryCustomerResponse.id() == null) {
            String msgError = "Unable to save temporary customer id";
            log.warn(msgError);
            throw new IllegalArgumentException(msgError);
        }
        historyBidSaved.setTemporaryCustomerId(temporaryCustomerResponse.id());
        repository.save(historyBidSaved);
        log.info("History Bid saved successfully temporary customer id");
    }


}
