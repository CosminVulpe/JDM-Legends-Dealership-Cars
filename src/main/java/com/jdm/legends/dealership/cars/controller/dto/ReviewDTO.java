package com.jdm.legends.dealership.cars.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record ReviewDTO(
        @NotEmpty
        @Size(min = 4, max = 35)
        String title,

        @NotEmpty
        @Size(min = 5)
        String description,

        @Min(1)
        @Max(5)
        int starRating
) {
}
