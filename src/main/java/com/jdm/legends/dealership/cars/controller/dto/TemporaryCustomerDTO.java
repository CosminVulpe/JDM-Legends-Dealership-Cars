package com.jdm.legends.dealership.cars.controller.dto;

import java.math.BigDecimal;

public record TemporaryCustomerDTO(
        Long id,
        String fullName,
        String userName,
        String emailAddress,
        String role,
        boolean checkInformationStoredTemporarily,
        BigDecimal bidValue
) {
}
