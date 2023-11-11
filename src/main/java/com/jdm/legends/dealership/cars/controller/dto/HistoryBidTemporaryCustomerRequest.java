package com.jdm.legends.dealership.cars.controller.dto;

import com.jdm.legends.dealership.cars.service.dto.HistoryBid;

import javax.validation.Valid;
import java.util.Optional;

public record HistoryBidTemporaryCustomerRequest(
        @Valid
        HistoryBidRequest historyBidRequest,

        HistoryBid  historyBid,

        @Valid
        TemporaryCustomerRequest temporaryCustomerRequest


) {
        public HistoryBidTemporaryCustomerRequest(HistoryBid historyBid, TemporaryCustomerRequest temporaryCustomerRequest) {
                this(null, historyBid, temporaryCustomerRequest);
        }
}
