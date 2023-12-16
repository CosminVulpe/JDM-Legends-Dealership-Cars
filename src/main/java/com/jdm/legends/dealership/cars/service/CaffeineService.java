package com.jdm.legends.dealership.cars.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jdm.legends.dealership.cars.controller.dto.CountryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class CaffeineService {
    private final static Cache<String, CountryResponse> cache = Caffeine.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).build();

    public void saveInCache(String key, CountryResponse response) {
        cache.put(key, response);
    }

    public Optional<CountryResponse> getCountryResponseCache(String key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }

    @Slf4j
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    public static final class KeyExpiredException extends RuntimeException {
        public KeyExpiredException(String message) {
            super(message);
            log.error(message);
        }
    }
}
