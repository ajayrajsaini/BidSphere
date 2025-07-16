package com.bidsphere.controller;


import com.bidsphere.dto.AuctionCompleteRequest;
import com.bidsphere.dto.AuctionRequest;
import com.bidsphere.dto.AuctionResponse;
import com.bidsphere.dto.RegisterRequest;
import com.bidsphere.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {
    private final AuctionService service;

    @PostMapping
    public ResponseEntity<AuctionResponse> createAuction(@RequestBody AuctionRequest request) {
        return ResponseEntity.ok(service.createAuction(request));
    }

    @GetMapping
    public ResponseEntity<List<AuctionResponse>> getAllAuctions() {
        return ResponseEntity.ok(service.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getAuctionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuctionResponse> updateAuction(@PathVariable UUID id, @RequestBody AuctionRequest request) {
        return ResponseEntity.ok(service.updateAuction(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable UUID id) {
        service.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AuctionResponse> cancelAuction(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancelAuction(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<AuctionResponse> completeAuction(@PathVariable UUID id, @RequestBody AuctionCompleteRequest auctionCompleteRequest) {
        return ResponseEntity.ok(service.completeAuction(id, auctionCompleteRequest));
    }
}
