package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.HistoryBid;
import com.example.demo.service.Repository.HistoryBidRepository;
import com.example.demo.service.exceptions.GetCarByIdNotFoundException;
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
        if (carService.doesCarExistById(car)) {
            throw new GetCarByIdNotFoundException();
        }

        car.addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);
        log.info("Bid Value with ID {} has been saved for the car with ID {}"
                , historyBid.getId(), car.getId());
    }
}
