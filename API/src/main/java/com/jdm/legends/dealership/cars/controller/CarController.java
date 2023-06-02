package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
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
    public List<HistoryBidInterface> getHistoryBidsList(@PathVariable("carId") Long carId) {
        return carService.getHistoryBidsList(carId);
    }

    @GetMapping(path = "/dates/{carId}")
    public List<LocalDateTime> getCarDates(@PathVariable("carId") Long carId) {
        return carService.getDateCar(carId);
    }

}