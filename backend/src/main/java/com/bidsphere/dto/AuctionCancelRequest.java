package com.bidsphere.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AuctionCancelRequest {
    @NotNull(message = "Reason is required")
    private String reason;
}
