package com.jdm.legends.dealership.cars.service.mapper;

import com.jdm.legends.dealership.cars.controller.dto.ReviewDTO;
import com.jdm.legends.dealership.cars.controller.dto.ReviewRequest;
import com.jdm.legends.dealership.cars.service.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "id", ignore = true)
    Review reviewRequestToReviewEntity(ReviewRequest request);

    ReviewDTO reviewEntityToReviewDTO(Review review);
}
