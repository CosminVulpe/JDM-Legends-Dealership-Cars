package com.jdm.legends.dealership.cars.controller.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record TemporaryCustomerRequest(

        @NotBlank
        String fullName,

        @NotBlank
        String userName,

        @Email
        String emailAddress,

        @Nullable
        String role,
        boolean checkInformationStoredTemporarily
) {
}
