package com.bidsphere.service;

import com.bidsphere.dto.AuctionCompleteRequest;
import com.bidsphere.dto.AuctionRequest;
import com.bidsphere.dto.AuctionResponse;
import com.bidsphere.model.Auction;
import com.bidsphere.model.AuctionStatus;
import com.bidsphere.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionResponse createAuction(AuctionRequest request) {
        Auction auction = new Auction();
        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());
        auction.setStartingPrice(request.getStartingPrice());
        auction.setCurrentPrice(request.getStartingPrice()); // Initial price = starting price
        auction.setStartTime(request.getStartTime());
        auction.setEndTime(request.getEndTime());
        auction.setSellerId(request.getSellerId());
        auction.setStatus(AuctionStatus.ACTIVE); // default status

        Auction saved = auctionRepository.save(auction);
        return toResponse(saved);
    }

    public List<AuctionResponse> getAllAuctions() {
        List<Auction> cachedAuctions = auctionCacheService.getAllAuctionsFromCache();
        List<AuctionResponse> responses = new ArrayList<>();
        if (cachedAuctions == null) {
            cachedAuctions = auctionRepository.findAll();
            auctionCacheService.cacheAllAuctions(cachedAuctions);
        }
        for (Auction auction : cachedAuctions) {
            AuctionResponse response = toResponse(auction);
            responses.add(response);
        }
        return responses;
    }

    @Autowired
    private AuctionCacheService auctionCacheService;

    public AuctionResponse getAuctionById(UUID id) {
        Auction cached = auctionCacheService.getAuctionFromCache(id.toString());
        if (cached != null) {
            return toResponse(cached);
        }
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isEmpty()) {
            throw new RuntimeException("Auction not found");
        }
        // Cache it for future
        auctionCacheService.cacheAuction(optionalAuction.get());
        return toResponse(optionalAuction.get());
    }

    public AuctionResponse updateAuction(UUID id, AuctionRequest request) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isEmpty()) {
            throw new RuntimeException("Auction not found");
        }
        Auction auction = optionalAuction.get();
        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());
        auction.setStartingPrice(request.getStartingPrice());
        auction.setStartTime(request.getStartTime());
        auction.setEndTime(request.getEndTime());
        Auction updated = auctionRepository.save(auction);
        // Sync cache

        auctionCacheService.removeAllAuctions();
        return toResponse(updated);
    }

    public void deleteAuction(UUID id) {
        auctionRepository.deleteById(id);
        auctionCacheService.removeAuctionCache(id.toString());
    }

    public AuctionResponse cancelAuction(UUID id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isEmpty()) {
            throw new RuntimeException("Auction not found");
        }
        Auction auction = optionalAuction.get();
        if (auction.getStatus() == AuctionStatus.CANCELLED) {
            throw new RuntimeException("Auction is already cancelled");
        }
        auction.setStatus(AuctionStatus.CANCELLED);

        Auction saved = auctionRepository.save(auction);

        auctionCacheService.cacheAuction(saved);
        auctionCacheService.removeAllAuctions();
        return toResponse(saved);
    }

    public AuctionResponse completeAuction(UUID id, AuctionCompleteRequest auctionCompleteRequest) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isEmpty()) {
            throw new RuntimeException("Auction not found");
        }
        Auction auction = optionalAuction.get();
        if (auction.getStatus() == AuctionStatus.COMPLETED) {
            throw new RuntimeException("Auction is already completed");
        }
        auction.setStatus(AuctionStatus.COMPLETED);
        auction.setBuyerId(auctionCompleteRequest.getBuyerId());
        if (auctionCompleteRequest.getRemarks() != null) {
            auction.setRemarks(auctionCompleteRequest.getRemarks());
        }
        Auction updatedAuction = auctionRepository.save(auction);
        auctionCacheService.cacheAuction(updatedAuction);
        auctionCacheService.removeAllAuctions();
        return toResponse(updatedAuction);
    }


    private AuctionResponse toResponse(Auction auction) {
        AuctionResponse response = new AuctionResponse();
        response.setId(auction.getId());
        response.setTitle(auction.getTitle());
        response.setDescription(auction.getDescription());
        response.setStartingPrice(auction.getStartingPrice());
        response.setCurrentPrice(auction.getCurrentPrice());
        response.setStatus(auction.getStatus());
        response.setStartTime(auction.getStartTime());
        response.setEndTime(auction.getEndTime());
        response.setSellerId(auction.getSellerId());
        response.setCreatedAt(auction.getCreatedAt());
        response.setUpdatedAt(auction.getUpdatedAt());
        if (auction.getStatus() == AuctionStatus.COMPLETED && auction.getBuyerId() != null) {
            response.setBuyerId(auction.getBuyerId());
        }
        if (auction.getRemarks() != null) {
            response.setRemarks(auction.getRemarks());
        }
        return response;
    }

}
