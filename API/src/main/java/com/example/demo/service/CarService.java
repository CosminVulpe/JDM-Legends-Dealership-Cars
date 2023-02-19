package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.service.Repository.CarRepository;
import com.example.demo.service.Repository.HistoryBidInterface;
import com.example.demo.service.exceptions.GetCarByIdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public ResponseEntity<List<Car>> getAllCars() {
        return (!carRepository.findAll().isEmpty()) ?
                ResponseEntity.ok(carRepository.findAll())
                : ResponseEntity.notFound().build();
    }

    public Car getCarById(Long carId) {
        return carRepository.findById(carId).orElseThrow(GetCarByIdNotFoundException::new);
    }

    public List<HistoryBidInterface> getHistoryBidsList(Long carId) {
        return carRepository.getBiggestHistoryBidByCarID(carId);
    }

    public List<LocalDateTime> getDatesCar(Long carId) {
        return new ArrayList<>(
                List.of(getCarById(carId).getStartDateCarPostedOnline()
                        , getCarById(carId).getDeadlineCarToSell())
        );
    }

}
