package com.jdm.legends.dealership.cars.service.repository;

import com.jdm.legends.dealership.cars.service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
