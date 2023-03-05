package com.example.demo.controller;

import com.example.demo.service.dto.Car;
import com.example.demo.service.CarService;
import com.example.demo.service.repository.HistoryBidInterface;
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
