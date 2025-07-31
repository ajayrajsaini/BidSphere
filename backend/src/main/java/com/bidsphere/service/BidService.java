package com.bidsphere.service;

import com.bidsphere.dto.BidRequest;
import com.bidsphere.dto.BidResponse;
import com.bidsphere.model.Auction;
import com.bidsphere.model.Bid;
import com.bidsphere.model.BidStatus;
import com.bidsphere.repository.AuctionRepository;
import com.bidsphere.repository.BidRepository;
import com.bidsphere.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    public BidResponse createBid(BidRequest bidRequest) {
        if (!userRepository.existsById(bidRequest.getBidderId())) {
            throw new RuntimeException("Bidder not found.");
        }
        Optional<Auction> optionalAuction= auctionRepository.findById(bidRequest.getAuctionId());
        if (optionalAuction == null) {
            throw new RuntimeException("Auction not found.");
        }

        if(optionalAuction.get().getCurrentPrice().compareTo(bidRequest.getAmount())>=0){
            throw new RuntimeException("Bid amount must be greater than the current price");
        }
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
