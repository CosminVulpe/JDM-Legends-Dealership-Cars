package com.example.demo.service;

import com.example.demo.service.dto.Car;
import com.example.demo.service.dto.HistoryBid;
import com.example.demo.service.repository.HistoryBidRepository;
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
