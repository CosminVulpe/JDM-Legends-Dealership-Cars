package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerDTO;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final TemporaryCustomerRepo temporaryCustomerRepo;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long carId) {
        return carRepository.findById(carId).orElseThrow(GetCarByIdNotFoundException::new);
    }

    public List<TemporaryCustomerDTO> getHistoryBidsList(Long carId) {
        return temporaryCustomerRepo.getAllTemporaryCustomerPerHistoryBid(getCarById(carId).getHistoryBidList());
    }

    public List<LocalDateTime> getDatesCar(Long carId) {
        return List.of(
                getCarById(carId).getStartDateCarPostedOnline(),
                getCarById(carId).getDeadlineCarToSell()
        );
    }

    @Slf4j
    @ResponseStatus(value = NOT_FOUND)
    public static class GetCarByIdNotFoundException extends RuntimeException {
        public GetCarByIdNotFoundException() {
            super("Car with Id provided does not exist");
            log.error("Car with Id provided does not exist");
        }
    }

}
