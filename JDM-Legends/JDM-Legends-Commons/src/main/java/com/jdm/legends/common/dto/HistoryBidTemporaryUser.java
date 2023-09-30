package com.jdm.legends.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HistoryBidTemporaryUser {
    private HistoryBid historyBid;
    private TemporaryUser temporaryUser;
}
