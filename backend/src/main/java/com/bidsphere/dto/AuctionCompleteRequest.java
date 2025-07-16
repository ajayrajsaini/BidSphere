package com.bidsphere.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AuctionCompleteRequest {
    @NotNull(message = "Buyer is required")
    private UUID buyerId;
    private String remarks = "";
}
