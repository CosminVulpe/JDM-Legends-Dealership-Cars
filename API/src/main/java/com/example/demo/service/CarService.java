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

    public Optional<Car> getCarById(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        return Optional
                .ofNullable(car
                        .orElseThrow(GetCarByIdNotFoundException::new)
                );
    }

    public void bid(Long carId, HistoryBid historyBid) {
        Optional<Car> getCar = getCarById(carId);
        if (doesCarExistById(getCar)) {
            throw new GetCarByIdNotFoundException();
        }

        getCar.get().addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);
        log.info("Bid Value with ID {} has been saved for the car with ID {}"
                , historyBid.getId(), getCar.get().getId());
    }

    private boolean doesCarExistById(Optional<Car> car) {
        return car.isEmpty();
    }

    public ResponseEntity<List<HistoryBid>> getHistoryBidsList(Long carId) {
        return ResponseEntity.ok(getCarById(carId).get().getHistoryBidList());
    }
}
