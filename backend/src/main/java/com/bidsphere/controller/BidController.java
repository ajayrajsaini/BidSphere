package com.bidsphere.controller;

import com.bidsphere.dto.BidRequest;
import com.bidsphere.dto.BidResponse;
import com.bidsphere.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

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

}
