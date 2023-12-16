package com.jdm.legends.dealership.cars.controller.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public record OrderRequest(
        @Valid
        List<AddressRequest> addressRequest,

        @NotBlank
        @Size(min = 3, max = 25)
        String phoneNumber,

        @NotBlank
        @Size(min = 3, max = 25)
        String portName
) {
}
