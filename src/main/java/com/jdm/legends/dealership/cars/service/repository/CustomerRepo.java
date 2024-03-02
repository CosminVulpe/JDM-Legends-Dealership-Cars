package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.controller.dto.CustomerDTO;
import com.jdm.legends.dealership.cars.controller.dto.CustomerIdResponse;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
public class CustomerRepo {
    private final RestTemplate restTemplate;
    private final String jdmLegendsCustomerUrl;
    private final HistoryBidRepository historyBidRepository;

    private static final String CUSTOMER = "/customer";

    public CustomerRepo(RestTemplate restTemplate
            , @Value("${jdm-legends.users.host}") String jdmLegendsCustomerUrl
            , HistoryBidRepository historyBidRepository) {
        this.restTemplate = restTemplate;
        this.jdmLegendsCustomerUrl = jdmLegendsCustomerUrl;
        this.historyBidRepository = historyBidRepository;
    }

    public void assignCustomerIdToHistoryBid(String emailDecoded, HistoryBid historyBidSaved) {
        try {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(jdmLegendsCustomerUrl + CUSTOMER + "/assign/{historyBidId}").buildAndExpand(historyBidSaved.getId());
            ResponseEntity<CustomerIdResponse> customerIdResponseResponseEntity = restTemplate.postForEntity(uriComponents.toUriString(), emailDecoded, CustomerIdResponse.class);

            if (!customerIdResponseResponseEntity.getStatusCode().is2xxSuccessful() || customerIdResponseResponseEntity.getBody() == null) {
                String msgError = "Retrieving customer id failed due to null body or unsuccessful request";
                log.error(msgError);
                throw new ResponseStatusException(INTERNAL_SERVER_ERROR, msgError);
            }

            historyBidSaved.setCustomerId(customerIdResponseResponseEntity.getBody().id());
            historyBidRepository.save(historyBidSaved);
        } catch (RestClientException e) {
            String msgError = "Unable to retrieve customer id";
            log.error(msgError, e);
            throw new RestClientException(msgError, e);
        }
    }

    public CustomerDTO getHistoryBidCustomerList(String authorization, HistoryBid historyBid) {
        try {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(jdmLegendsCustomerUrl + CUSTOMER + "/getHistoryBids/{historyBid}")
                    .buildAndExpand(historyBid.getId());
            ResponseEntity<CustomerDTO> entity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET
                    , new HttpEntity<>(getHttpHeaders(authorization))
                    , CustomerDTO.class);

            if (!entity.getStatusCode().is2xxSuccessful() || !entity.hasBody()) {
                String msgError = "Retrieving history bid for customer failed";
                log.error(msgError);
                throw new ResponseStatusException(INTERNAL_SERVER_ERROR, msgError);
            }

            CustomerDTO customerDTO = entity.getBody();
            return new CustomerDTO(customerDTO.id(), customerDTO.fullName()
                    , customerDTO.userName(), customerDTO.emailAddress(), historyBid.getBidValue());
        } catch (RestClientException e) {
            String msgError = "Unable to retrieve history bids list";
            log.error(msgError, e);
            throw new RestClientException(msgError, e);
        }
    }

    private HttpHeaders getHttpHeaders(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorization);
        return headers;
    }
}
