package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.OrderRequest;
import com.jdm.legends.dealership.cars.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add/{carId}")
    public void addOrder(@RequestBody @Valid OrderRequest orderRequest, @PathVariable Long carId) {
        orderService.addOrder(orderRequest, carId);
    }
}
