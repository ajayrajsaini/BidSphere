package com.bidsphere.service;

import com.bidsphere.dto.BidRequest;
import com.bidsphere.dto.BidResponse;
import com.bidsphere.model.Bid;
import com.bidsphere.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<BidResponse> getAllBid(){
        List<BidResponse> responses = new ArrayList<>();
        List<Bid> allBids = bidRepository.findAll();
        for(Bid bid : allBids){
            responses.add(generateBidResponse(bid));
        }
        return responses;
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
