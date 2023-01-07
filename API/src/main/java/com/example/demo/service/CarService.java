package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.service.DAO.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public ResponseEntity<List<Car>> getAllCars() {
        return (!carRepository.findAll().isEmpty()) ?
                ResponseEntity.ok(carRepository.findAll())
                : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Car> getCarById(Long carId) {
        return carRepository
                .findById(carId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .badRequest()
                        .body(null));
    }

}
