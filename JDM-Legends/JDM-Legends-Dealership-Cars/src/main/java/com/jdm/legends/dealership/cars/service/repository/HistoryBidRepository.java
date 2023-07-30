package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.common.dto.HistoryBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryBidRepository extends JpaRepository<HistoryBid, Long> {

}
