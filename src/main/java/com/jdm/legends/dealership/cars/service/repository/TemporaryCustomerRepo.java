package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.controller.dto.OrderIdRequest;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerDTO;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerIdResponse;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerDTO;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class TemporaryCustomerRepo {
    private final RestTemplate restTemplate;
    private final String serverHost;
    private final int serverPort;
    private final HistoryBidRepository repository;

    private static final String TEMPORARY_CUSTOMER = "/temporary-customer";

    public TemporaryCustomerRepo(RestTemplate restTemplate
            , @Value("${server.host}") String serverHost
            , @Value("${jdm-legends.users.port}") int serverPort
            , HistoryBidRepository repository) {
        this.restTemplate = restTemplate;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.repository = repository;
    }

    public void saveTempUser(TemporaryCustomerRequest temporaryCustomerRequest, HistoryBid historyBidSaved) {
        try {
            UriComponents uriRequest = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + TEMPORARY_CUSTOMER + "/save/{historyBidId}").buildAndExpand(historyBidSaved.getId());
            TemporaryCustomerIdResponse temporaryCustomerIdResponse = restTemplate.postForObject(uriRequest.toUriString(), new HttpEntity<>(temporaryCustomerRequest), TemporaryCustomerIdResponse.class);

            log.info("Sending request {} to url: {} to save temporary customer", temporaryCustomerRequest, uriRequest);
            if (isNull(temporaryCustomerIdResponse) || isNull(temporaryCustomerIdResponse.id())) {
                String msgError = "Temporary customer id cannot be null";
                log.error(msgError);
                throw new ResponseStatusException(INTERNAL_SERVER_ERROR, msgError);
            }

            historyBidSaved.setTemporaryCustomerId(temporaryCustomerIdResponse.id());
            repository.save(historyBidSaved);
            log.info("History bid saved successfully");

        } catch (RestClientException e) {
            String msgError = "Unable to save temporary customer id";
            log.error(msgError, e);
            throw new RestClientException(msgError, e);
        }
    }

    public List<TemporaryCustomerDTO> getAllTemporaryCustomerPerHistoryBid(List<HistoryBid> historyBidList) {
        return historyBidList.stream().map(historyBid -> {
                    UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + TEMPORARY_CUSTOMER + "/get/history/{historyBidId}").buildAndExpand(String.valueOf(historyBid.getId()));
                    ResponseEntity<List<TemporaryCustomerDTO>> restTemplateForEntity = restTemplate.exchange(uriComponents.toUriString(), GET, null, new ParameterizedTypeReference<>() {
                    });

                    if (!restTemplateForEntity.getStatusCode().is2xxSuccessful() || isNull(restTemplateForEntity.getBody())) {
                        String msgError = "Unable to get temporary customer by Id";
                        log.error(msgError);
                        throw new RestClientException(msgError);
                    }

                    List<TemporaryCustomerDTO> entityBodyList = restTemplateForEntity.getBody();
                    log.info("Getting response from {} {} ", uriComponents, entityBodyList);
                    return entityBodyList.stream().map(entityBody -> new TemporaryCustomerDTO(entityBody.id()
                                    , entityBody.fullName(), entityBody.userName()
                                    , entityBody.emailAddress(), entityBody.role()
                                    , entityBody.checkInformationStoredTemporarily()
                                    , historyBid.getBidValue()))
                            .toList();
                }).flatMap(Collection::stream)
                .sorted(Comparator.comparing(TemporaryCustomerDTO::bidValue)
                        .reversed()).limit(7).toList();
    }

    public WinnerCustomerDTO getWinnerCustomer(Long carId) {
        try {
            UriComponents uriRequest = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + TEMPORARY_CUSTOMER + "/get/winner/{carId}").buildAndExpand(carId);
            ResponseEntity<WinnerCustomerDTO> winnerCustomerDTO = restTemplate.getForEntity(uriRequest.toUriString(), WinnerCustomerDTO.class);

            WinnerCustomerDTO body = winnerCustomerDTO.getBody();
            if (!winnerCustomerDTO.getStatusCode().is2xxSuccessful() || body == null) {
                String msgError = String.format("Request to %s was not successful", uriRequest.toUriString());
                log.error(msgError);
                throw new ResponseStatusException(winnerCustomerDTO.getStatusCode(), msgError);
            }

            return body;
        } catch (RestClientException e) {
            String msgError = "Unable to get winner temporary customer";
            log.error(msgError, e);
            throw new RestClientException(msgError, e);
        }
    }

    public void assignOrderIdToTempCustomer(Long tempCustomerId, Long orderId) {
        UriComponents uriRequest = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + TEMPORARY_CUSTOMER + "/assign/{tempCustomerId}").buildAndExpand(tempCustomerId);
        restTemplate.postForObject(uriRequest.toUriString(), new OrderIdRequest(orderId), OrderIdRequest.class);
    }

}
