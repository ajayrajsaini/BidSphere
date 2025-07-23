package com.bidsphere.controller;

import com.bidsphere.dto.BidRequest;
import com.bidsphere.dto.BidResponse;
import com.bidsphere.model.BidStatus;
import com.bidsphere.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @PostMapping("/createBid")
    public ResponseEntity<BidResponse> createBid(@RequestBody BidRequest bidRequest){
        return ResponseEntity.ok(bidService.createBid(bidRequest));
    }
    @GetMapping("/getAllBid")
    public ResponseEntity<List<BidResponse>> createBid(){
        return ResponseEntity.ok(bidService.getAllBid());
    }
    @PutMapping("/updateBidAmount/{id}")
    public ResponseEntity<BidResponse> updateBid(@PathVariable UUID id, @RequestBody BigDecimal newAmount){
        return ResponseEntity.ok(bidService.updateBidAmount(id, newAmount));
    }
    @PutMapping("/updateBidStatus/{id}")
    public ResponseEntity<BidResponse> updateBid(@PathVariable UUID id, @RequestBody BidStatus newStatus){
        return ResponseEntity.ok(bidService.updateBidStatus(id, newStatus));
    }

}
