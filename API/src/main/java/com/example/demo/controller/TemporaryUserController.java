package com.example.demo.controller;

import com.example.demo.service.dto.TemporaryUser;
import com.example.demo.service.TemporaryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/temporary-user")
@RequiredArgsConstructor
public class TemporaryUserController {
    private final TemporaryUserService service;

    @PostMapping
    public void saveTemporaryUser(@RequestBody TemporaryUser temporaryUser) {
        service.saveTemporaryUser(temporaryUser);
    }
}
