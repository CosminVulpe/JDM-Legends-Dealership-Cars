package com.jdm.legends.dealership.cars.service.mapper;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidRequest;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoryBidMapper {
    HistoryBidMapper INSTANCE = Mappers.getMapper(HistoryBidMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "temporaryCustomerId", ignore = true)
    HistoryBid historyBidRequestToHistoryBidEntity(HistoryBidRequest request);
}
