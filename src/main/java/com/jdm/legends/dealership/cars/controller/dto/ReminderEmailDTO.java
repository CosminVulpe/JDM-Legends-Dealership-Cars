package com.jdm.legends.dealership.cars.controller.dto;

import java.time.LocalDateTime;

public record ReminderEmailDTO(
        LocalDateTime sentTimeEmail,
        LocalDateTime enterTimeEmail,
        Long temporaryCustomerId,
        LocalDateTime deadLineEmail
) {
}
