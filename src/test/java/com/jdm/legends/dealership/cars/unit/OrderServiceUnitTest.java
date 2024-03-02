package com.jdm.legends.dealership.cars.unit;

import com.jdm.legends.dealership.cars.controller.dto.WinnerCustomerDTO;
import com.jdm.legends.dealership.cars.service.AddressService;
import com.jdm.legends.dealership.cars.service.CarService;
import com.jdm.legends.dealership.cars.service.OrderService;
import com.jdm.legends.dealership.cars.service.entity.Car;
import com.jdm.legends.dealership.cars.service.entity.Order;
import com.jdm.legends.dealership.cars.service.mapper.AddressMapper;
import com.jdm.legends.dealership.cars.service.repository.OrderRepository;
import com.jdm.legends.dealership.cars.service.repository.TemporaryCustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.jdm.legends.dealership.cars.utils.TestDummy.getAddressRequestMock;
import static com.jdm.legends.dealership.cars.utils.TestDummy.getOrderRequestMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private TemporaryCustomerRepo temporaryCustomerRepo;
    @Mock
    private CarService carService;


    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldAddOrderWhenCarIsNotReserved() {
        when(carService.getCarById(any())).thenReturn(Car.builder().isCarReserved(true).build());
        when(addressService.addAddress(any())).thenReturn(AddressMapper.INSTANCE.addressRequestToAddressEntity(getAddressRequestMock()));
        when(temporaryCustomerRepo.getWinnerCustomer(any())).thenReturn(new WinnerCustomerDTO(1L, 2L));
        when(orderRepository.save(any())).thenReturn(new Order("+54672367453", 1L,"Marine"));

        orderService.addOrder(getOrderRequestMock(), 1L);

        verify(orderRepository).save(any());
        verify(temporaryCustomerRepo).assignOrderIdToTempCustomer(any(), any());
    }

    @Test
    void shouldNotAddOrderWhenCarIsReserved() {
        when(carService.getCarById(any())).thenReturn(Car.builder().isCarReserved(false).build());

        orderService.addOrder(getOrderRequestMock(), 1L);

        verify(orderRepository, never()).save(any());
        verify(temporaryCustomerRepo, never()).assignOrderIdToTempCustomer(any(), any());
    }
}
