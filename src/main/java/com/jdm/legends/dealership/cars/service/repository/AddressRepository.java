package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
