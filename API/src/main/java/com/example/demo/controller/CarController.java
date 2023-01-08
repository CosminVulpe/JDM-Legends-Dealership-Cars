package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.model.HistoryBid;
import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        return carService.getAllCars();
    }

    @GetMapping(path = "/{carId}")
    public Optional<Car> getCarById(@PathVariable("carId") Long carId) {
        return carService.getCarById(carId);
    }

    @PostMapping(path = "/bid/{carId}")
    public void bidValueToCar(@PathVariable("carId") Long carId
            , @RequestBody HistoryBid historyBid) {
        carService.bid(carId, historyBid);
    }

    @GetMapping(path = "/bid-list/{carId}")
    public ResponseEntity<List<HistoryBid>> getHistoryBidsList(@PathVariable("carId") Long carId){
        return carService.getHistoryBidsList(carId);
    }
}
