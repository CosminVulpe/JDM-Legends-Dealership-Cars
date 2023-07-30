package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.common.dto.TemporaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryUserRepository extends JpaRepository<TemporaryUser, Long> {
}
