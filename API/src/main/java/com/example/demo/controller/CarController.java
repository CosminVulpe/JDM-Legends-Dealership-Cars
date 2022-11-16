package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.model.enums.CarCompany;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        return carService.getAllCars();
    }

    @GetMapping(path = "/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable("carId") Long carId){
        return carService.getCarById(carId);
    }
}
