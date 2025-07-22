package com.bidsphere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="bids", indexes = {
        @Index(name = "idx_bids_auction_id", columnList = "auction_id"),
        @Index(name = "idx_bids_bidder_id", columnList = "bidder_id"),
        @Index(name = "idx_bids_amount", columnList = "amount"),
        @Index(name = "idx_bids_timestamp", columnList = "timestamp"),
        @Index(name = "idx_bids_auction_amount", columnList = "auction_id, amount DESC"),
        @Index(name = "idx_bids_bid_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "auction_id", nullable = false)
    private UUID auctionId;

    @Column(name = "bidder_id", nullable = false)
    private UUID bidderId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timeStamp = LocalDateTime.now();

    @Column(nullable = false)
    private BidStatus status = BidStatus.VALID;

    private String reason;

}
