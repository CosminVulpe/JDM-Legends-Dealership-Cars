package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerDTO;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerIdResponse;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
        UriComponents uriRequest = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/temporary-customer/save/{historyBidId}").buildAndExpand(historyBidSaved.getId());
        TemporaryCustomerIdResponse temporaryCustomerIdResponse = restTemplate.postForObject(uriRequest.toUriString(), new HttpEntity<>(temporaryCustomerRequest), TemporaryCustomerIdResponse.class);

        log.info("Sending request {} to url: {} to save temporary customer", temporaryCustomerRequest, uriRequest);
        if (isNull(temporaryCustomerIdResponse) && isNull(temporaryCustomerIdResponse.id())) {
            String msgError = "Unable to save temporary customer id";
            log.error(msgError);
            throw new TemporaryCustomerException(msgError);
        }
        historyBidSaved.setTemporaryCustomerId(temporaryCustomerIdResponse.id());
        repository.save(historyBidSaved);
        log.info("History bid saved successfully");
    }

    public List<TemporaryCustomerDTO> getAllTemporaryCustomerPerHistoryBid(List<HistoryBid> historyBidList) {
        return historyBidList.stream().map(historyBid -> {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/temporary-customer/{temporaryCustomerId}").buildAndExpand(historyBid.getId());
            ResponseEntity<TemporaryCustomerDTO> restTemplateForEntity = restTemplate.getForEntity(uriComponents.toUriString(), TemporaryCustomerDTO.class);

            if (!restTemplateForEntity.getStatusCode().is2xxSuccessful() && isNull(restTemplateForEntity.getBody())) {
                String msgError = "Unable to get temporary customer by Id";
                log.error(msgError);
                throw new RestClientException(msgError);
            }
            TemporaryCustomerDTO entityBody = restTemplateForEntity.getBody();
            log.info("Getting response from {} {} ", uriComponents, entityBody);
            return new TemporaryCustomerDTO(
                    entityBody.id(), entityBody.fullName(), entityBody.userName(),
                    entityBody.emailAddress(), entityBody.role(),
                    entityBody.checkInformationStoredTemporarily(), historyBid.getBidValue()
            );
        }).sorted(Comparator.comparing(TemporaryCustomerDTO::bidValue).reversed()).limit(7).toList();
    }

    @Slf4j
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    public static final class TemporaryCustomerException extends RuntimeException{
        public TemporaryCustomerException(String message) {
            super(message);
            log.error(message);
        }
    }
}
