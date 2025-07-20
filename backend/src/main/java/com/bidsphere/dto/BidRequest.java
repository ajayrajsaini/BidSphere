package com.bidsphere.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BidRequest {
    private UUID auctionId;
    private UUID bidderId;
    private BigDecimal amount;
}