package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.service.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
