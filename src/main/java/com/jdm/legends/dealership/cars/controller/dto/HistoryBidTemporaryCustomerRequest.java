package com.jdm.legends.dealership.cars.controller.dto;

import com.jdm.legends.dealership.cars.service.dto.HistoryBid;

import javax.validation.Valid;

public record HistoryBidTemporaryCustomerRequest(
        HistoryBid historyBid,
        @Valid
        TemporaryCustomerRequest temporaryCustomerRequest
) {
}
