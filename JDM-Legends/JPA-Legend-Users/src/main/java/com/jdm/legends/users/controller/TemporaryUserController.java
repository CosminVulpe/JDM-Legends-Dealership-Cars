package com.jdm.legends.users.controller;

import com.jdm.legends.common.dto.TemporaryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/temporary-user")
public class TemporaryUserController {

    @PostMapping(path = "/save")
    public void saveTempUser(@RequestBody @Valid TemporaryUser temporaryUser){
    }
}
