package com.example.legend.service.constants;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class Constants {
    public static final Integer AVAILABLE_DAYS_TO_PURCHASE = 3;
    public static final LocalDateTime MOCK_DATA = now().plusMinutes(10);
//    public static final LocalDateTime MOCK_DATA = now().plusDays(3);
}
