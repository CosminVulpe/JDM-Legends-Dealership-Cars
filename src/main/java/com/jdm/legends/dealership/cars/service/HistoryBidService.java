package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidRequest;
import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import com.jdm.legends.dealership.cars.service.mapper.HistoryBidMapper;
import com.jdm.legends.dealership.cars.service.repository.CustomerRepo;
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
    private final CustomerRepo customerRepo;

    public void bid(Long carId, HistoryBidTemporaryCustomerRequest request) {
        HistoryBid historyBid = computeHistoryBidSave(carId, request.historyBidRequest());
        String customerEmail = request.historyBidRequest().customerEmail();
        if (customerEmail == null || customerEmail.isBlank()) {
            temporaryCustomerRepo.saveTempUser(request.temporaryCustomerRequest(), historyBid);
            log.info("Bid Value saved for the car with ID {}", historyBid.getCar().getId());
        }
    }

    public void bidCustomer(Long carId, HistoryBidRequest historyBidRequest) {
        HistoryBid historyBid = computeHistoryBidSave(carId, historyBidRequest);
        customerRepo.assignCustomerIdToHistoryBid(historyBidRequest.customerEmail(), historyBid);
    }

    private HistoryBid computeHistoryBidSave(Long carId, HistoryBidRequest historyBidRequest) {
        Car car = carService.getCarById(carId);
        HistoryBid historyBid = HistoryBidMapper.INSTANCE.historyBidRequestToHistoryBidEntity(historyBidRequest);
        historyBid.setCar(car);

        car.addHistoryBid(historyBid);
        return historyBidRepository.save(historyBid);
    }
}
