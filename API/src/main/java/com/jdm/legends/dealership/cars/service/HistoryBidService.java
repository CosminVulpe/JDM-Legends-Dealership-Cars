package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.service.dto.Car;
import com.jdm.legends.dealership.cars.service.dto.HistoryBid;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryBidService {
    private final HistoryBidRepository historyBidRepository;
    private final CarService carService;
    public void bid(Long carId, HistoryBid historyBid) {
        Car car = carService.getCarById(carId);

        car.addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);
        log.info("Bid Value with ID {} has been saved for the car with ID {}"
                , historyBid.getId(), car.getId());
    }
}
