package com.example.demo.service;

import com.example.demo.service.dto.Car;
import com.example.demo.service.repository.CarRepository;
import com.example.demo.service.repository.HistoryBidInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return  carRepository.findAll();
    }

    public Car getCarById(Long carId) {
        return carRepository.findById(carId).orElseThrow(GetCarByIdNotFoundException::new);
    }

    public List<HistoryBidInterface> getHistoryBidsList(Long carId) {
        return carRepository.getBiggestHistoryBidByCarID(carId);
    }

    public List<LocalDateTime> getDateCar(Long carId) {
        return new ArrayList<>(
                List.of(getCarById(carId).getStartDateCarPostedOnline()
                        , getCarById(carId).getDeadlineCarToSell())
        );
    }

    @Slf4j
    @ResponseStatus(value = NOT_FOUND, reason = "Car with Id provided does not exist")
    public static class GetCarByIdNotFoundException extends RuntimeException {
        public GetCarByIdNotFoundException() {
            super();
            log.error("Car with Id provided does not exist");
        }
    }

}
