package com.bidsphere.repository;

import com.bidsphere.model.Auction;
import com.bidsphere.model.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuctionRepository extends JpaRepository<Auction, UUID> {
    List<Auction> findByStatus(AuctionStatus status);
    List<Auction> findBySellerId(UUID sellerId);
}
