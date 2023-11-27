package com.jdm.legends.dealership.cars.controller.dto;

import java.math.BigDecimal;

public record WinnerCustomerResponse(BigDecimal bidValue, Long historyBidId) {
}
