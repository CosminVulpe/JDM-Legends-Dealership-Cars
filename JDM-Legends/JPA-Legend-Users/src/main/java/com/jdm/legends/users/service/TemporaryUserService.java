package com.jdm.legends.users.service;

import com.jdm.legends.users.repository.TemporaryUserRepository;
import com.jdm.legends.users.repository.WinnerUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository temporaryUserRepository;

    public WinnerUser getWinnerUser(Long carId) {
        return temporaryUserRepository.getWinnerUser(carId);
    }
}
