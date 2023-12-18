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

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.Collection;
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
        return temporaryCustomerRepo.getAllTemporaryCustomerPerHistoryBid(
                getCarById(carId).getHistoryBidList()
        );
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

        if (!carById.isCarReserved()) {
            carById.setCarReserved(true);
            carRepository.save(carById);
        }

        HistoryBid historyBid = historyBidList.stream().max(Comparator.comparing(HistoryBid::getBidValue))
                .stream().min(Comparator.comparing(HistoryBid::getTimeOfTheBid)).orElseThrow();

        return Optional.of(new WinnerCustomerResponse(historyBid.getBidValue(), historyBid.getId()));
    }

    public void cancelReservation(Long tempCustomerId) {
        try {
            List<HistoryBid> historyBids = carRepository.findAll().stream()
                    .map(Car::getHistoryBidList)
                    .flatMap(Collection::stream)
                    .toList();
            HistoryBid historyBid = historyBids.stream().filter(item -> item.getTemporaryCustomerId().equals(tempCustomerId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("No Temporary Customer found for cancelling the reservation"));
            Car car = historyBid.getCar();

            if (car.isCarReserved()) {
                return;
            }
            car.setCarReserved(false);
            carRepository.save(car);

            log.info("Reservation for car {} was cancelled for temporary customer {} ", car.getId(), tempCustomerId);
        } catch (PersistenceException e) {
            log.error("Something went wrong while cancelling the reservation", e);
        }
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
