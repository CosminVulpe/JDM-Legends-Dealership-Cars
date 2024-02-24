package com.jdm.legends.dealership.cars.controller.dto;

import javax.validation.Valid;

public record HistoryBidTemporaryCustomerRequest(
        @Valid
        HistoryBidRequest historyBidRequest,
        @Valid
        TemporaryCustomerRequest temporaryCustomerRequest
) {
}
