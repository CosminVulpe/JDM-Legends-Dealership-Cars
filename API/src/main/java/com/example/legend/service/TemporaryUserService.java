package com.example.legend.service;

import com.example.legend.service.dto.TemporaryUser;
import com.example.legend.service.repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository temporaryUserRepository;

    public void saveTemporaryUser(TemporaryUser temporaryUser) {
        temporaryUserRepository.save(temporaryUser);
    }

    @Scheduled(fixedDelay = 2_592_000_000L, initialDelay = 604_800_000)
    private void deleteTemporaryUsersOlderThan30Days() {
        temporaryUserRepository.deleteAll(temporaryUserRepository.getAllTemporaryUsersOlderThan30Days());
    }
}
