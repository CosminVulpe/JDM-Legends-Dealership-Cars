package com.jdm.legends.users.controller;

import com.jdm.legends.users.service.TemporaryUserService;
import com.jdm.legends.users.service.dto.TemporaryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/temporary-user")
public class TemporaryUserController {
    private final TemporaryUserService service;

    @PostMapping
    public void addTempUser(@RequestBody @Valid TemporaryUser temporaryUser) {
        service.addTemporaryUser(temporaryUser);
    }
}
