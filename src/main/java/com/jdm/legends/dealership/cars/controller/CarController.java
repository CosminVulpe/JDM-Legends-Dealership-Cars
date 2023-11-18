package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerDTO;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<Car> getAll() {
        return carService.getAllCars();
    }

    @GetMapping(path = "/{carId}")
    public Car getCarById(@PathVariable("carId") Long carId) {
        return carService.getCarById(carId);
    }

    @GetMapping(path = "/bid-list/{carId}")
    public List<TemporaryCustomerDTO> getHistoryBidsList(@PathVariable("carId") Long carId) {
        return carService.getHistoryBidsList(carId);
    }

    @GetMapping(path = "/dates/{carId}")
    public List<LocalDateTime> getCarDates(@PathVariable("carId") Long carId) {
        return carService.getDatesCar(carId);
    }

}
