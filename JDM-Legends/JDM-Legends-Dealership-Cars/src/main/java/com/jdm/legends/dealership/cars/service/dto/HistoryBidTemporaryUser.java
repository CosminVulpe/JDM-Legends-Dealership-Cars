package com.jdm.legends.dealership.cars.service.dto;

import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.TemporaryUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HistoryBidTemporaryUser {
    private HistoryBid historyBid;
    private TemporaryUser temporaryUser;
}
