package com.bidsphere.service;

import com.bidsphere.model.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuctionCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "auction::";

    public void cacheAuction(Auction auction) {
        String key = PREFIX + auction.getId();
        redisTemplate.opsForValue().set(key, auction, 60, TimeUnit.MINUTES);
    }

    public Auction getAuctionFromCache(String id) {
        String key = PREFIX + id;
        Object cached = redisTemplate.opsForValue().get(key);
        return cached instanceof Auction ? (Auction) cached : null;
    }

    public void removeAuctionCache(String id) {
        redisTemplate.delete(PREFIX + id);
    }
}