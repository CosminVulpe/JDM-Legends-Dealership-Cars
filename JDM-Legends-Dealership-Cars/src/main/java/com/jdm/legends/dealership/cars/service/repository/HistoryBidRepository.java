package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryBidRepository extends JpaRepository<HistoryBid, Long> {

}
