package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.HistoryBid;
import com.example.demo.service.Repository.CarRepository;
import com.example.demo.service.Repository.HistoryBidRepository;
import com.example.demo.service.exceptions.GetCarByIdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final HistoryBidRepository historyBidRepository;

    public ResponseEntity<List<Car>> getAllCars() {
        return (!carRepository.findAll().isEmpty()) ?
                ResponseEntity.ok(carRepository.findAll())
                : ResponseEntity.notFound().build();
    }

    public Car getCarById(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        return car.orElse(null);
    }

    public void bid(Long carId, HistoryBid historyBid) {
        Car car = getCarById(carId);
        if (doesCarExistById(car)) {
            throw new GetCarByIdNotFoundException();
        }

        car.addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);
        log.info("Bid Value with ID {} has been saved for the car with ID {}"
                , historyBid.getId(), car.getId());
    }

    public ResponseEntity<List<HistoryBid>> getHistoryBidsList(Long carId) {
        Car carById = getCarById(carId);
        return doesCarExistById(carById)
                ? ResponseEntity.notFound().build() : ResponseEntity.ok(carById.getHistoryBidList());
    }

    private boolean doesCarExistById(Car car) {
        return car == null;
    }
}
