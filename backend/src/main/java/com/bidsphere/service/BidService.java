package com.bidsphere.service;

import com.bidsphere.dto.BidRequest;
import com.bidsphere.dto.BidResponse;
import com.bidsphere.model.Bid;
import com.bidsphere.model.BidStatus;
import com.bidsphere.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;

    public BidResponse createBid(BidRequest bidRequest) {
        Bid bid = new Bid();
        bid.setAuctionId(bidRequest.getAuctionId());
        bid.setBidderId(bidRequest.getBidderId());
        bid.setAmount(bidRequest.getAmount());
        Bid bidSaved = bidRepository.save(bid);
        return generateBidResponse(bidSaved);
    }

    public List<BidResponse> getAllBid() {
        List<BidResponse> responses = new ArrayList<>();
        List<Bid> allBids = bidRepository.findAll();
        for (Bid bid : allBids) {
            responses.add(generateBidResponse(bid));
        }
        return responses;
    }

    public BidResponse updateBidAmount(UUID id, BigDecimal newAmount) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        if (optionalBid == null) {
            throw new RuntimeException("Bid not found");
        }
        Bid bid = optionalBid.get();
        if (bid.getAmount().compareTo(newAmount) < 0) {
            bid.setAmount(newAmount);
        } else {
            throw new RuntimeException("New amount is less than the older bid amount.");
        }
        return generateBidResponse(bidRepository.save(bid));
    }
    public BidResponse updateBidStatus(UUID id, BidStatus newStatus) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        if (optionalBid == null) {
            throw new RuntimeException("Bid not found");
        }
        Bid bid = optionalBid.get();
        bid.setStatus(newStatus);

        return generateBidResponse(bidRepository.save(bid));
    }

    public BidResponse generateBidResponse(Bid bid) {
        BidResponse bidResponse = new BidResponse();
        bidResponse.setId(bid.getId());
        bidResponse.setAuctionId(bid.getAuctionId());
        bidResponse.setBidderId(bid.getBidderId());
        bidResponse.setAmount(bid.getAmount());
        bidResponse.setTimestamp(bid.getTimeStamp());
        bidResponse.setStatus(bid.getStatus());
        bidResponse.setReason(bid.getReason());
        return bidResponse;
    }
}
