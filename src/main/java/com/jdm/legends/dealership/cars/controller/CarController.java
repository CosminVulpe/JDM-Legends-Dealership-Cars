package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.CustomerDTO;
import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerDTO;
import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerResponse;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
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
    public List<TemporaryCustomerDTO> getHistoryBidsList(@PathVariable("carId") Long carId) {
        return carService.getHistoryBidsList(carId);
    }

    @GetMapping(path = "/bid-list-customer/{carId}")
    public List<CustomerDTO> getHistoryBidCustomerList(@PathVariable("carId") Long carId, @RequestHeader("Authorization") String authorization) {
        return carService.getHistoryBidCustomerList(carId, authorization);
    }

    @GetMapping(path = "/dates/{carId}")
    public List<LocalDateTime> getCarDates(@PathVariable("carId") Long carId) {
        return carService.getDatesCar(carId);
    }

    @GetMapping(path = "/max/bidValue/{carId}")
    public Optional<WinnerCustomerResponse> getMaxBidValue(@PathVariable("carId") Long carId) {
        return carService.getMaxBid(carId);
    }

    @GetMapping(path = "/cancelReservation")
    public void cancelReservationCar(@RequestParam(name = "tempCustomerId") Long tempCustomerId) {
        carService.cancelReservation(tempCustomerId);
    }

}
