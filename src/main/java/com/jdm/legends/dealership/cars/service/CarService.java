package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.TemporaryCustomerDTO;
import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerResponse;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.HistoryBid;
import com.jdm.legends.dealership.cars.service.repository.CarRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final TemporaryCustomerRepo temporaryCustomerRepo;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long carId) {
        return carRepository.findById(carId).orElseThrow(CarByIdException::new);
    }

    public List<TemporaryCustomerDTO> getHistoryBidsList(Long carId) {
        return temporaryCustomerRepo.getAllTemporaryCustomerPerHistoryBid(getCarById(carId).getHistoryBidList());
    }

    public List<LocalDateTime> getDatesCar(Long carId) {
        return List.of(
                getCarById(carId).getStartDateCarPostedOnline(),
                getCarById(carId).getDeadlineCarToSell()
        );
    }

    public Optional<WinnerCustomerResponse> getMaxBid(Long cardId) {
        Car carById = getCarById(cardId);
        List<HistoryBid> historyBidList = carById.getHistoryBidList();

        if (historyBidList.isEmpty()) {
            log.warn("No max bid was found");
            return Optional.empty();
        }

        HistoryBid historyBid = historyBidList.stream().max(Comparator.comparing(HistoryBid::getBidValue))
                .stream().min(Comparator.comparing(HistoryBid::getTimeOfTheBid)).orElseThrow();

        return Optional.of(new WinnerCustomerResponse(historyBid.getBidValue(), historyBid.getId()));
    }

    @Slf4j
    @ResponseStatus(value = NOT_FOUND)
    public static class CarByIdException extends RuntimeException {
        public CarByIdException() {
            super("Unable to retrieve a specific car");
            log.error("Unable to retrieve a specific car");
        }
    }

}
