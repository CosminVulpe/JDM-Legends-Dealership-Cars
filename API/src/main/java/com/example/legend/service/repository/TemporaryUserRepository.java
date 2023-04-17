package com.example.legend.service.repository;

import com.example.legend.service.dto.TemporaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporaryUserRepository extends JpaRepository<TemporaryUser, Long> {
    @Query(value = "SELECT * FROM temporary_user WHERE temporary_user.time_of_the_creation < (NOW() - INTERVAL '30 days')", nativeQuery = true)
    List<TemporaryUser> getAllTemporaryUsersOlderThan30Days();
}
