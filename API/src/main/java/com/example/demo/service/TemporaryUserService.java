package com.example.demo.service;

import com.example.demo.service.dto.TemporaryUser;
import com.example.demo.service.repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository temporaryUserRepository;

    public void saveTemporaryUser(TemporaryUser temporaryUser) {
        temporaryUserRepository.save(temporaryUser);
    }
}
