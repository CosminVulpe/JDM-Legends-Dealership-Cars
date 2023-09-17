package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.common.dto.Car;
import com.jdm.legends.common.dto.HistoryBid;
import com.jdm.legends.common.dto.HistoryBidTemporaryUser;
import com.jdm.legends.common.dto.TemporaryUser;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryUserRepo;
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

    private static final String POTENTIAL_CLIENT = "Potential Client";
    private static final String ANONYMOUS = "Anonymous";

    public void bid(Long carId, HistoryBidTemporaryUser request) {
        Car car = carService.getCarById(carId);
        HistoryBid historyBid = request.getHistoryBid();
        TemporaryUser temporaryUser = request.getTemporaryUser();

        car.addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);

        temporaryUser.setRole((temporaryUser.isCheckInformationStoredTemporarily()) ? POTENTIAL_CLIENT : ANONYMOUS);
        temporaryUserRepo.saveTempUser(request);
        log.info("Bid Value saved for the car with ID {}", car.getId());
    }
}
