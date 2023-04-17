package com.example.legend.controller;

import com.example.legend.service.dto.HistoryBid;
import com.example.legend.service.HistoryBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*")
@RequestMapping(path = "/history-bid")
@RequiredArgsConstructor
public class HistoryBidController {
    private final HistoryBidService historyBidService;

    @PostMapping(path = "/bid/{carId}")
    public void bidValueToCar(@PathVariable("carId") Long carId
            , @RequestBody HistoryBid historyBid) {
        historyBidService.bid(carId, historyBid);
    }

    @GetMapping(path = "/car-winner/{carId}")
    public Map<String, Object> getWinnerCarAuction(@PathVariable("carId") Long carId) {
        return historyBidService.getWinnerCarAuction(carId);
    }
}
