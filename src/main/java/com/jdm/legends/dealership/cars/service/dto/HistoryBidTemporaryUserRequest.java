package com.jdm.legends.dealership.cars.service.dto;

import lombok.*;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HistoryBidTemporaryUserRequest {
    private HistoryBid historyBid;
    private TemporaryUser temporaryUser;
}
