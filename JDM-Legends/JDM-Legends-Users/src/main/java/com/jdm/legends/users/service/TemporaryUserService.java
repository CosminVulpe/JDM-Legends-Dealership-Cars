package com.jdm.legends.users.service;

import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.users.repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository repository;

    public List<TemporaryUser> getAllTempUsers(){
        return repository.findAll();
    }
}
