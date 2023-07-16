package com.jdm.legends.dealership.cars.service.repository;

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
@RequiredArgsConstructor
public class TemporaryUserRepo {
    private final RestTemplate restTemplate;

    @Value("${server.host}")
    private String serverHost;

    @Value("${jdm-legends.users.port}")
    private int serverPort;

    public void saveTempUser(TemporaryUser temporaryUser) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(serverHost + serverPort + "/temporary-user/save");
        ResponseEntity<TemporaryUser> temporaryUserResponseEntity = restTemplate.postForEntity(uriComponentsBuilder.toUriString()
                , new HttpEntity<>(temporaryUser), TemporaryUser.class);

        if (!temporaryUserResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new SaveTemporaryUserError();
        }
    }


    @Slf4j
    @ResponseStatus(value = INTERNAL_SERVER_ERROR, reason = "Error while trying to save temporary user")
    private static class SaveTemporaryUserError extends RuntimeException {
        public SaveTemporaryUserError() {
            super();
            log.error("Error while trying to save temporary user");
        }
    }
}
