package com.jdm.legends.dealership.cars.service.mapper;

import com.jdm.legends.dealership.cars.controller.dto.AddressRequest;
import com.jdm.legends.dealership.cars.service.entity.Address;
import com.jdm.legends.dealership.cars.service.enums.AddressType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper  {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addressType", expression = "java(getAddressType(request))")
    Address addressRequestToAddressEntity(AddressRequest request);

    default AddressType getAddressType(AddressRequest request) {
        return request.isAddressDomicile() ? AddressType.DOMICILE : AddressType.RESIDENCE;
    }
}
