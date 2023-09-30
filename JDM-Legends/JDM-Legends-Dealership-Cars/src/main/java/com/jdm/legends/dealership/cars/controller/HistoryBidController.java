package com.jdm.legends.dealership.cars.controller;

import com.jdm.legends.common.dto.HistoryBidTemporaryUser;
import com.jdm.legends.dealership.cars.service.HistoryBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/history-bid")
@RequiredArgsConstructor
public class HistoryBidController {
    private final HistoryBidService historyBidService;

    @PostMapping(path = "/bid/{carId}")
    public void bidValueToCar(@PathVariable("carId") Long carId, @RequestBody HistoryBidTemporaryUser request) {
        historyBidService.bid(carId, request);
    }

}
