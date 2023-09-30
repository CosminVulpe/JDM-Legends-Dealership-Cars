package com.jdm.legends.users.repository;

import com.jdm.legends.common.dto.TemporaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TemporaryUserRepository extends JpaRepository<TemporaryUser, Long> {
    @Query(value = """
                SELECT ts.user_name AS userName, MAX(hb.bid_value) AS bidValue
                FROM temporary_user_history_bid tshb
                         INNER JOIN history_bid hb ON tshb.history_bid_id = hb.id
                         INNER JOIN temporary_user ts ON tshb.temporary_user_id = ts.id
                         INNER JOIN cars c ON hb.car_id = c.id
                WHERE c.id = ?1
                GROUP BY ts.id
                LIMIT 1;
            """, nativeQuery = true)
    Optional<WinnerUser> getWinnerUser(Long carId);
}
