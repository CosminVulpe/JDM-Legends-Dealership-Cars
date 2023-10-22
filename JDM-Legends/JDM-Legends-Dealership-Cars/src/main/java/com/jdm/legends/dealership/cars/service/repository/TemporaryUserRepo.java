package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.common.dto.HistoryBidTemporaryUser;
import com.jdm.legends.common.dto.TemporaryUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class TemporaryUserRepo {
    private final RestTemplate restTemplate;
    private final String serverHost;
    private final int serverPort;

    public TemporaryUserRepo(RestTemplate restTemplate, @Value("${server.host}") String serverHost, @Value("${jdm-legends.users.port}") int serverPort) {
        this.restTemplate = restTemplate;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void saveTempUser(HistoryBidTemporaryUser historyBidTemporaryUser) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/temporary-user/save");
        ResponseEntity<TemporaryUser> temporaryUserResponseEntity = restTemplate.postForEntity(uriComponentsBuilder.toUriString(), new HttpEntity<>(historyBidTemporaryUser), TemporaryUser.class);

        if (!temporaryUserResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new SaveTemporaryUserException();
        }
    }


    @Slf4j
    @ResponseStatus(value = INTERNAL_SERVER_ERROR, reason = "Error while trying to save temporary user")
    private static class SaveTemporaryUserException extends RuntimeException {
        public SaveTemporaryUserException() {
            super();
            log.error("Error while trying to save temporary user");
        }
    }
}
