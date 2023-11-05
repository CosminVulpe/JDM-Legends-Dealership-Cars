package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryBidService {
    private final CarService carService;
    private final TemporaryCustomerRepo temporaryCustomerRepo;
    private final HistoryBidRepository historyBidRepository;

    public void bid(Long carId, HistoryBidTemporaryCustomerRequest request) {
        Car car = carService.getCarById(carId);
        HistoryBid historyBid = request.historyBid();

        car.addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);

        temporaryCustomerRepo.saveTempUser(request);
        log.info("Bid Value saved for the car with ID {}", car.getId());
    }
}
