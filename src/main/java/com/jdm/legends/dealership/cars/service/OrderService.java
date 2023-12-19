package com.jdm.legends.dealership.cars.service;

import com.jdm.legends.dealership.cars.controller.dto.OrderRequest;
import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerDTO;
import com.jdm.legends.dealership.cars.service.entity.Address;
import com.jdm.legends.dealership.cars.service.entity.Order;
import com.jdm.legends.dealership.cars.service.repository.OrderRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.joining;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final TemporaryCustomerRepo temporaryCustomerRepo;
    private final CarService carService;

    @Transactional
    public void addOrder(OrderRequest orderRequest, Long carId) {
        List<Address> addressList = orderRequest.addressRequest().stream().map(addressService::addAddress).toList();
        WinnerCustomerDTO winnerCustomer = temporaryCustomerRepo.getWinnerCustomer(carId);

        Long temporaryCustomerId = winnerCustomer.tempCustomerId();
        Order order = new Order(orderRequest.phoneNumber(), temporaryCustomerId, orderRequest.portName());
        String ids = addressList.stream().map(Address::getId).map(String::valueOf).collect(joining((addressList.size() == 1) ? "" : ", "));
        addressList.forEach(address -> order.addAddressToOrder(address, ids));

        Order orderSaved = orderRepository.save(order);
        log.info("Order placed successfully");
        carService.calculateQuantityInStock(carId);
        temporaryCustomerRepo.assignOrderIdToTempCustomer(temporaryCustomerId, orderSaved.getId());
    }

}
