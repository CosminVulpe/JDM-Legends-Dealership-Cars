package com.jdm.legends.dealership.cars.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Roles {
    POTENTIAL_CLIENT("Potential Client"),
    ANONYMOUS("Anonymous"),
    ADMIN("Admin");

    @Getter
    private final String value;
}
