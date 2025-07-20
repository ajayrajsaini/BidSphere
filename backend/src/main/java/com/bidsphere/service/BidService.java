package com.bidsphere.service;

import com.bidsphere.dto.BidRequest;
import com.bidsphere.dto.BidResponse;
import com.bidsphere.model.Bid;
import com.bidsphere.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    public BidResponse createBid(BidRequest bidRequest){
        Bid bid = new Bid();
        bid.setAuctionId(bidRequest.getAuctionId());
        bid.setBidderId(bidRequest.getBidderId());
        bid.setAmount(bidRequest.getAmount());
        Bid bidSaved = bidRepository.save(bid);
        return generateBidResponse(bidSaved);
    }

    public BidResponse generateBidResponse(Bid bid){
        BidResponse bidResponse = new BidResponse();
        bidResponse.setId(bid.getId());
        bidResponse.setAuctionId(bid.getAuctionId());
        bidResponse.setBidderId(bid.getBidderId());
        bidResponse.setAmount(bid.getAmount());
        bidResponse.setTimestamp(bid.getTimeStamp());
        bidResponse.setReason(bid.getReason());
        return bidResponse;
    }
}
