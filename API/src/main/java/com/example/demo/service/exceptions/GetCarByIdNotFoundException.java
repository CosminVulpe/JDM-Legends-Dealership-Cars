package com.example.demo.service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ResponseStatus(value = NOT_FOUND, reason = "Car with Id provided does not exist")
public class GetCarByIdNotFoundException extends RuntimeException {
    public GetCarByIdNotFoundException() {
        super();
        log.error("Car with Id provided does not exist");
    }
}
