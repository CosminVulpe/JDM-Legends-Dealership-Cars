package com.jdm.legends.users.service;

import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.users.repository.TemporaryUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TemporaryUserServiceTest {

    @Mock
    private TemporaryUserRepository repository;

    @InjectMocks
    private TemporaryUserService temporaryUserService;

    @Test
    void getAllTempUsersSuccessfully() {
        when(repository.findAll()).thenReturn(getTempUsersMockData());

        List<TemporaryUser> allTempUsers = temporaryUserService.getAllTempUsers();

        verify(repository).findAll();
        assertFalse(allTempUsers.isEmpty());

        assertEquals(getTempUsersMockData().size(), allTempUsers.size());
    }

    private List<TemporaryUser> getTempUsersMockData() {
        return List.of(
                TemporaryUser.builder().userName("tes12").fullName("John Mick").emailAddress("john12@gmail.com").build(),
                TemporaryUser.builder().userName("harry66").fullName("Harry Style").emailAddress("harrymusic@gmail.com").build(),
                TemporaryUser.builder().userName("therock").fullName("The Rock").emailAddress("sevenbucksprod@gmail.com").build()
        );
    }
}
