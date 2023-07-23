package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.common.dto.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = """
            SELECT hb.id AS id, hb.bid_value AS bidValue, tu.user_name AS userName
            FROM history_bid hb
            INNER JOIN cars c on hb.car_id = c.id
            INNER JOIN temporary_user_history_bid tuhb on hb.id = tuhb.history_bid_id
            INNER JOIN temporary_user tu on tuhb.temporary_user_id = tu.id
            WHERE c.id = :id
            ORDER BY hb.bid_value DESC LIMIT 7;
            """, nativeQuery = true)
    List<HighestBid> getBiggestHistoryBidByCarID(Long id);

}
