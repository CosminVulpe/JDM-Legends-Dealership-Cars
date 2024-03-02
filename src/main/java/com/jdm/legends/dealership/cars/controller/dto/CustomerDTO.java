package com.jdm.legends.dealership.cars.controller.dto;

import java.math.BigDecimal;

public record CustomerDTO(Long id,
                          String fullName,
                          String userName,
                          String emailAddress,
                          BigDecimal bidValue
) {
}
