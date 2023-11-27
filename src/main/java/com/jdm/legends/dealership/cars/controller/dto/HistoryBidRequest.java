package com.jdm.legends.dealership.cars.controller.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoryBidRequest(
        @NotNull
        BigDecimal bidValue,

        @NotNull
        LocalDateTime timeOfTheBid
) {
}
