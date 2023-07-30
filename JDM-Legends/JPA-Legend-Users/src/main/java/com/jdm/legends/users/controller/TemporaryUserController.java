package com.jdm.legends.users.controller;

import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.users.repository.WinnerUser;
import com.jdm.legends.users.service.TemporaryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/temporary-user")
public class TemporaryUserController {
    private final TemporaryUserService service;

    @PostMapping(path = "/save")
    public void saveTempUser(@RequestBody @Valid TemporaryUser temporaryUser){
        // ToDo resolve issue restTemplate ManyToMany mapping
    }

    @GetMapping(path = "/winner/{carId}")
    public WinnerUser getWinner(@PathVariable Long carId){
        return service.getWinnerUser(carId);
    }
}
