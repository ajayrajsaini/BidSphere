package com.bidsphere.dto;

import com.bidsphere.model.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AuctionResponse {
    private UUID id;
    private String title;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private AuctionStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID sellerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
