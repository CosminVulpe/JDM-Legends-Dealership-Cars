package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.dealership.cars.service.dto.HistoryBidTemporaryUser;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryBidService {
    private final CarService carService;
    private final TemporaryUserRepo temporaryUserRepo;
    private final HistoryBidRepository historyBidRepository;
    private final TemporaryUserRepository temporaryUserRepository;

    private static final String POTENTIAL_CLIENT = "Potential Client";
    private static final String ANONYMOUS = "Anonymous";

    public void bid(Long carId, HistoryBidTemporaryUser request) {
        Car car = carService.getCarById(carId);
        HistoryBid historyBid = request.getHistoryBid();
        TemporaryUser temporaryUser = request.getTemporaryUser();

        car.addHistoryBid(historyBid);
        HistoryBid historyBidSaved = historyBidRepository.save(historyBid);

        temporaryUser.setRole((temporaryUser.isCheckInformationStoredTemporarily()) ? POTENTIAL_CLIENT : ANONYMOUS);
        temporaryUser.addHistoryBid(historyBidSaved);

        temporaryUserRepository.save(temporaryUser);

        log.info("Bid Value saved for the car with ID {}", car.getId());
    }

}
