package com.jdm.legends.dealership.cars.service.repository;

import java.math.BigDecimal;

public interface HighestBid {
    Integer getId();

    BigDecimal getBidValue();

    String getUserName();

}
