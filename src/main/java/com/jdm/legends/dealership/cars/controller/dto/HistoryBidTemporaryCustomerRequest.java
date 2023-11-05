package com.jdm.legends.dealership.cars.controller.dto;

import com.jdm.legends.dealership.cars.service.dto.HistoryBid;

import javax.validation.constraints.NotNull;

public record HistoryBidTemporaryCustomerRequest(
        @NotNull
        HistoryBid historyBid,

        @NotNull
        TemporaryCustomerDTO temporaryCustomerDTO
) {
}
