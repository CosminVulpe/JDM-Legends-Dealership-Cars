package com.example.legend.controller;

import com.example.legend.service.TemporaryUserService;
import com.example.legend.service.dto.TemporaryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/temporary-user")
@RequiredArgsConstructor
public class TemporaryUserController {
    private final TemporaryUserService service;

    @PostMapping
    public void saveTemporaryUser(@RequestBody @Valid TemporaryUser temporaryUser) {
        service.saveTemporaryUser(temporaryUser);
    }
}
