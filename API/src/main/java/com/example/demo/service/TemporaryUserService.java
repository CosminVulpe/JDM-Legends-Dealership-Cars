package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.TemporaryUser;
import com.example.demo.service.Repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository temporaryUserRepository;
    private final CarService carService;

    public void saveTemporaryUser(TemporaryUser temporaryUser) {
        temporaryUserRepository.save(temporaryUser);
    }
}
