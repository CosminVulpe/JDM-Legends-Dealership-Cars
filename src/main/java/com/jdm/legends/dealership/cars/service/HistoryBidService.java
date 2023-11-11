package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidRequest;
import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import com.jdm.legends.dealership.cars.service.mapping.Mapper;
import com.jdm.legends.dealership.cars.service.repository.HistoryBidRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryBidService {
    private final CarService carService;
    private final TemporaryCustomerRepo temporaryCustomerRepo;
    private final HistoryBidRepository historyBidRepository;

    public void bid(Long carId, HistoryBidTemporaryCustomerRequest request) {
        Car car = carService.getCarById(carId);
        Mapper<HistoryBidRequest, HistoryBid> mapper = (HistoryBidRequest source) ->
                HistoryBid.builder()
                        .bidValue(source.bidValue())
                        .timeOfTheBid(source.timeOfTheBid())
                        .car(car)
                        .temporaryUsersList(new HashSet<>())
                        .build();

        HistoryBid historyBid = mapper.map(request.historyBidRequest());

        car.addHistoryBid(historyBid);
        historyBidRepository.save(historyBid);

        temporaryCustomerRepo.saveTempUser(new HistoryBidTemporaryCustomerRequest(historyBid, request.temporaryCustomerRequest()));
        log.info("Bid Value saved for the car with ID {}", car.getId());
    }
}
