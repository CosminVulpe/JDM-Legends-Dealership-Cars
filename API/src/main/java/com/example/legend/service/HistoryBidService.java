package com.example.legend.service;

import com.example.legend.service.dto.Car;
import com.example.legend.service.dto.HistoryBid;
import com.example.legend.service.repository.HistoryBidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

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

    public Map<String, Object> getWinnerCarAuction(Long carId) {
        Map<String, Object> records = new HashMap<>();
        Car carById = carService.getCarById(carId);
        List<Double> priceAuction = carById.getHistoryBidList().stream().map(HistoryBid::getBidValue).toList();

        if (isEmpty(priceAuction)) {
            return null;
        }

        String customer = "CUSTOMER";
        records.put(customer, customer);
        records.put("PRIZE", Collections.max(priceAuction));
        log.info("Winner of the car {} with id {} is {}", carById.getCarName(), carById.getId(), customer);
        return records;
    }
}
