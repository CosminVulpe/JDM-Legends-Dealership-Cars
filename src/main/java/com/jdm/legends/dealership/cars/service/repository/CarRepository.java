package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.service.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = """
            SELECT hb.id AS id, hb.bid_value AS bidValue, tu.user_name AS userName, tu.role AS role, tu.check_information_stored_temporarily AS checkInformationStoredTemporarily
            FROM history_bid hb
                INNER JOIN cars c on hb.car_id = c.id
                INNER JOIN temporary_customer_history_bid tuhb on hb.id = tuhb.history_bid_id
                INNER JOIN temporary_customer tu on tuhb.temporary_customer_id = tu.id
            WHERE c.id = :id
            ORDER BY hb.bid_value DESC LIMIT 7;
            """, nativeQuery = true)
    List<HighestBid> getBiggestHistoryBidByCarID(Long id);

}
