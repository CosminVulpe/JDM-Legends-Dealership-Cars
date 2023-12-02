package com.jdm.legends.dealership.cars.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AddressRequest(

        @NotBlank
        @Size(min = 5, max = 100)
        String streetName,

        @NotBlank
        @Size(min = 5, max = 45)
        String cityName,

        @NotBlank
        @Size(min = 3, max = 20)
        String postalCode,

        @NotBlank
        @Size(min = 3, max = 15)
        String region,

        boolean isAddressDomicile
) {
}
