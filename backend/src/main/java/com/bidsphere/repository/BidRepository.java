package com.bidsphere.repository;

import com.bidsphere.model.Auction;
import com.bidsphere.model.AuctionStatus;
import com.bidsphere.model.Bid;
import com.bidsphere.model.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {
    List<Bid> findByStatus(BidStatus status);
    List<Bid> findByBidderId(UUID bidderId);
}
