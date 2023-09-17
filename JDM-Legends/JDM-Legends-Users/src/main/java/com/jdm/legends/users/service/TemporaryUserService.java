package com.jdm.legends.users.service;

import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.users.repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemporaryUserService {
    private final TemporaryUserRepository repository;

    public List<TemporaryUser> getAllTempUsers(){
        return repository.findAll();
    }

    public void saveUser(@Valid TemporaryUser temporaryUser, HistoryBid historyBid) {
        temporaryUser.addHistoryBid(historyBid);
        repository.save(temporaryUser);
        log.info("Successfully saved temporary user");
    }

}
