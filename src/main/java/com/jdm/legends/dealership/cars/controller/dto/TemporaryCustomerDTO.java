package com.jdm.legends.dealership.cars.controller.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;

public record TemporaryCustomerDTO(

        @Nullable
        String fullName,

        @Nullable
        String userName,

        @Email
        String emailAddress,

        @Nullable
        String role,
        boolean checkInformationStoredTemporarily
) {
}
