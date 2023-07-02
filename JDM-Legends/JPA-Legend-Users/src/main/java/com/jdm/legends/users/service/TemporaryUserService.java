package com.jdm.legends.users.service;

import com.jdm.legends.users.service.dto.TemporaryUser;
import com.jdm.legends.users.service.repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository repository;

    public void addTemporaryUser(TemporaryUser temporaryUser) {
        repository.save(temporaryUser);
    }
}
