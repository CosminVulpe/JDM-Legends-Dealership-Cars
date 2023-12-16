package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.service.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
