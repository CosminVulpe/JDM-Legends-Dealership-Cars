package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import com.jdm.legends.dealership.cars.service.mapper.HistoryBidMapper;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryBidService {
    private final CarService carService;
    private final TemporaryCustomerRepo temporaryCustomerRepo;
    private final HistoryBidRepository historyBidRepository;

    @Transactional
    public void bid(Long carId, HistoryBidTemporaryCustomerRequest request) {
        Car car = carService.getCarById(carId);
        HistoryBid historyBid = HistoryBidMapper.INSTANCE.historyBidRequestToHistoryBidEntity(request.historyBidRequest());
        historyBid.setCar(car);

        car.addHistoryBid(historyBid);
        HistoryBid historyBidSaved = historyBidRepository.save(historyBid);

        temporaryCustomerRepo.saveTempUser(request.temporaryCustomerRequest(), historyBidSaved);
        log.info("Bid Value saved for the car with ID {}", car.getId());
    }
}
