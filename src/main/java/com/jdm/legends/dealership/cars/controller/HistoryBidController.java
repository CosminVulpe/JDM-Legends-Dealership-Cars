package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.dealership.cars.controller.dto.HistoryBidRequest;
import com.jdm.legends.dealership.cars.controller.dto.HistoryBidTemporaryCustomerRequest;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/history-bid")
@RequiredArgsConstructor
public class HistoryBidController {
    private final HistoryBidService historyBidService;

    @PostMapping("/bid/{carId}")
    public void bidValueToCar(@PathVariable("carId") Long carId
            , @RequestBody @Valid HistoryBidTemporaryCustomerRequest request) {
        historyBidService.bid(carId, request);
    }

    @PostMapping("/bid/customer/{carId}")
    public void bidValueToCar(@PathVariable("carId") Long carId
            , @RequestBody @Valid HistoryBidRequest historyBidRequest) {
        historyBidService.bidCustomer(carId, historyBidRequest);
    }
}
